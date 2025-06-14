package shop.matddang.matddangbe.sale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.suitableCrops.domain.SuitableCrops;
import shop.matddang.matddangbe.sale.dto.SaleRequestDto;
import shop.matddang.matddangbe.sale.repository.SaleRepository;
import shop.matddang.matddangbe.sale.repository.SuitableCropsRepository;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final SuitableCropsRepository suitableCropsRepository;  // 생성자 주입으로 변경

    public List<Sale> findBySaleId(Long saleId) {
        return saleRepository.findBySaleId(saleId);
    }

    public List<Sale> findAllById(Iterable<Long> saleIdList) {
        return saleRepository.findAllById(saleIdList);
    }

    public List<Sale> searchSales(SaleRequestDto requestDto) {

        // ---------------------------------------- 데이터 전처리 ----------------------------------------
        // 비었을 경우, 필터가 없을 때 -> 모두

        //거래 유형 지정
        if (requestDto.getSaleCategoryList().isEmpty()) {
            requestDto.setSaleCategoryList(List.of("임대", "매매"));
        }

        //최소 가격 지정
        if (requestDto.getMinPrice() == null || requestDto.getMinPrice().compareTo(BigDecimal.ZERO) < 0) {
            requestDto.setMinPrice(BigDecimal.ZERO);
        }

        //최대 가격 지정
        if (requestDto.getMaxPrice() == null || requestDto.getMaxPrice().compareTo(BigDecimal.ZERO) < 0) {
            requestDto.setMaxPrice(BigDecimal.valueOf(1000000000)); // 현재 db의 가격 최대값은 950000000
        }

        //최소 면적 지정
        if (requestDto.getMinArea() == null || requestDto.getMinArea().compareTo(BigDecimal.ZERO) < 0) {
            requestDto.setMinArea(BigDecimal.ZERO);
        }

        //최대 면적 지정
        if (requestDto.getMaxArea() == null || requestDto.getMaxArea().compareTo(BigDecimal.ZERO) < 0) {
            requestDto.setMaxArea(BigDecimal.valueOf(1000000000)); // 현재 db의 최대값은 950000000
        }

        //토지 유형 지정
        if (requestDto.getLandCategoryList().isEmpty()) {
            requestDto.setLandCategoryList(List.of("전_전", "답_답", "과수원"));
        }
        // ---------------------------------------- 데이터 전처리 완료 ----------------------------------------

        return saleRepository.searchBySaleFilter(
                requestDto.getSaleCategoryList(),
                requestDto.getMinPrice(), // 최소 가격
                requestDto.getMaxPrice(), // 최대 가격
                requestDto.getMinArea(), // 최소 면적
                requestDto.getMaxArea(), // 최대 면적
                requestDto.getLandCategoryList()
        );
    }

    public List<SuitableCrops> findBySaleIdInAndCropIdIn(List<Long> saleIds, List<Long> cropIds) {
        return suitableCropsRepository.findBySaleIdInAndCropIdIn(saleIds, cropIds);
    }

    // Sale 저장
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

}
