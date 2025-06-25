package shop.matddang.matddangbe.sale.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.Liked.domain.Liked;
import shop.matddang.matddangbe.Liked.repository.LikedRepository;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.dto.SaleDetailResponseDto;
import shop.matddang.matddangbe.sale.dto.SimilarSaleDto;
import shop.matddang.matddangbe.suitableCrops.domain.SuitableCrops;
import shop.matddang.matddangbe.sale.dto.SaleRequestDto;
import shop.matddang.matddangbe.sale.repository.SaleRepository;
import shop.matddang.matddangbe.sale.repository.SuitableCropsRepository;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SuitableCropsRepository suitableCropsRepository;  // 생성자 주입으로 변경
    private final LikedRepository likedRepository;
    private final AmazonS3 amazonS3;
    public SaleDetailResponseDto findBySaleId(Long saleId) {

        // 1. 현재 매물 조회 및 이미지 URL 세팅
        List<Sale> saleList = saleRepository.findBySaleId(saleId);
        Sale sale = saleList.get(0);
        sale.setImgUrl("https://matddang.s3.ap-northeast-2.amazonaws.com/saleimgs/" + sale.getSaleId() + ".png");

        // 2. 유사 매물 ID 무작위 추출 (자기 자신 제외)
        List<Long> allIds = new ArrayList<>();
        for (long i = 1; i <= 257; i++) {
            if (i != saleId) allIds.add(i);
        }
        Collections.shuffle(allIds);
        List<Long> randomIds = allIds.stream().limit(5).collect(Collectors.toList());

        // 3. 유사 매물 조회
        List<Sale> similarSalesEntity = saleRepository.findBySaleIdIn(randomIds);

        // 4. DTO 변환
        List<SimilarSaleDto> similarSales = similarSalesEntity.stream()
                .map(s -> {
                    SimilarSaleDto dto = new SimilarSaleDto();
                    dto.setSaleId(s.getSaleId());
                    dto.setSaleCategory(s.getSaleCategory());
                    dto.setLandType(s.getLandType());
                    dto.setSaleAddr(s.getSaleAddr());
                    dto.setLandCategory(s.getLandCategory());
                    dto.setPrice(s.getPrice());
                    dto.setArea(s.getArea());
                    dto.setMainCrop(s.getMainCrop());
                    dto.setImgUrl("https://matddang.s3.ap-northeast-2.amazonaws.com/saleimgs/" + s.getSaleId() + ".png");
                    return dto;
                })
                .collect(Collectors.toList());


        SaleDetailResponseDto responseDto = new SaleDetailResponseDto();
        responseDto.setSale(List.of(sale));
        responseDto.setSimilarSales(similarSales);

        return responseDto;
    }

    public List<Sale> findBySaleAddrLike(String keyword) {
        String searchKeyword = "%" + keyword + "%";
        return saleRepository.findBySaleAddrLike(searchKeyword);
    }


    public List<Sale> findAllById(Iterable<Long> saleIdList) {
        return saleRepository.findAllById(saleIdList);
    }

    public List<Sale> findNearBy(List<Sale> saleList, List<Double> zoom) {
        if (zoom == null || zoom.size() < 4) {
            throw new IllegalArgumentException("zoom 배열에 4개 값이 필요합니다.");
        }

        double topLat = zoom.get(0);   // 북쪽(큰 위도)
        double leftLng = zoom.get(1);   // 서쪽(작은 경도)
        double bottomLat = zoom.get(2);   // 남쪽(작은 위도)
        double rightLng = zoom.get(3);   // 동쪽(큰 경도)

        // 혹시 값이 뒤바뀌어 들어와도 안전하게
        double minLat = Math.min(topLat, bottomLat);
        double maxLat = Math.max(topLat, bottomLat);
        double minLng = Math.min(leftLng, rightLng);
        double maxLng = Math.max(leftLng, rightLng);

        return saleList.stream()
                .filter(sale -> {
                    Double lat = sale.getWgsY();
                    Double lng = sale.getWgsX();
                    return lat != null && lng != null &&
                            lat >= minLat && lat <= maxLat &&
                            lng >= minLng && lng <= maxLng;
                })
                .collect(Collectors.toList());
    }

    public List<Sale> searchSales(SaleRequestDto requestDto) {

        // ---------------------------------------- 데이터 전처리 ----------------------------------------
        // 비었을 경우, 필터가 없을 때 -> 모두

        //거래 유형 지정
        if (requestDto.getSaleCategoryList() == null || requestDto.getSaleCategoryList().isEmpty()) {
            requestDto.setSaleCategoryList(List.of("임대", "매매"));
        }

        //토지 유형 지정
        if (requestDto.getLandCategoryList() == null || requestDto.getLandCategoryList().isEmpty()) {
            requestDto.setLandCategoryList(List.of("전_전", "답_답", "과수원"));
        }
        // ---------------------------------------- 데이터 전처리 완료 ----------------------------------------
        /* ---------- 2) 필터 검색 ---------- */
        List<Sale> sales = saleRepository.searchBySaleFilter(
                requestDto.getSaleCategoryList(),
                requestDto.getMinPrice(),
                requestDto.getMaxPrice(),
                requestDto.getMinArea(),
                requestDto.getMaxArea(),
                requestDto.getLandCategoryList()
        );

        /* ---------- 매물번호 → 이미지 URL 매핑 ---------- */
        final String bucket = "matddang";
        final String prefix = "saleimgs/";

        sales.forEach(sale -> {
            String key = prefix + sale.getSaleId() + ".png";
            URL url = amazonS3.getUrl(bucket, key);
            sale.setImgUrl(url.toString());
        });

        return sales;
    }

    public List<SuitableCrops> findBySaleIdInAndCropIdIn(List<Long> saleIds, List<Long> cropIds) {
        return suitableCropsRepository.findBySaleIdInAndCropIdIn(saleIds, cropIds);
    }

    // Sale 저장
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    //거리 기반 정렬
    public List<Sale> getsearchSalesByLocation(List<Double> location, List<Sale> saleList) {
        return saleList.stream()
                .sorted(Comparator.comparingDouble(sale ->
                        calculateDistance(location.get(0), location.get(1), sale.getWgsY(), sale.getWgsX())
                ))
                .collect(Collectors.toList());
    }

    //거리 계산
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c; // 단위: km
    }

    public void getSortSales(List<Sale> saleList, String sortBy) {
        if (sortBy == null || saleList == null || saleList.isEmpty()) return;

        Comparator<Sale> comparator = null;

        switch (sortBy) {
            case "profit":
                comparator = Comparator.comparing(Sale::getProfit);
                break;
            case "liked":
                getSortSalesByLiked(saleList); // liked는 별도 정렬 메서드
                return;
            case "both":
                comparator = Comparator.comparing(Sale::getProfit);

                List<Long> saleIds = saleList.stream()
                        .map(Sale::getSaleId)
                        .collect(Collectors.toList());

                Set<Long> likedSaleIds = likedRepository.findBySaleIdIn(saleIds)
                        .stream()
                        .map(Liked::getSaleId)
                        .collect(Collectors.toSet());

                Comparator<Sale> likedComparator = Comparator.comparing(
                        sale -> likedSaleIds.contains(sale.getSaleId()) // 좋아요가 있으면 앞에, 없으면 뒤에
                );

                // 수익 기준 정렬 후 liked 기준 정렬
                comparator = comparator.thenComparing(likedComparator);
                break;
            default:
                return;
        }

        if (comparator != null) {
            comparator = comparator.reversed(); // desc
            saleList.sort(comparator); // 리스트 정렬
        }
    }


    public void getSortSalesByLiked(List<Sale> saleList) {
        if (saleList == null || saleList.isEmpty()) return;

        List<Long> saleIds = saleList.stream()
                .map(Sale::getSaleId)
                .collect(Collectors.toList());

        // 좋아요된 매물 ID 목록 조회
        List<Liked> likedList = likedRepository.findBySaleIdIn(saleIds);

        Set<Long> likedSaleIds = likedList.stream()
                .map(Liked::getSaleId)
                .collect(Collectors.toSet());

        Comparator<Sale> comparator = Comparator.comparing(
                sale -> likedSaleIds.contains(sale.getSaleId())
        );

        saleList.sort(comparator.reversed());  // 좋아요가 있는 매물이 앞에 오게 정렬
    }


}
