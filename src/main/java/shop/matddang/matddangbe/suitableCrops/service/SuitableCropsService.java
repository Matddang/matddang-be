package shop.matddang.matddangbe.suitableCrops.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.crop.domain.Crop;
import shop.matddang.matddangbe.crop.service.CropService;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.dto.SaleDetailResponseDto;
import shop.matddang.matddangbe.sale.repository.SaleRepository;
import shop.matddang.matddangbe.sale.repository.SuitableCropsRepository;
import shop.matddang.matddangbe.sale.service.SaleService;
import shop.matddang.matddangbe.suitableCrops.domain.SuitableCrops;
import shop.matddang.matddangbe.suitableCrops.dto.SuitableCropsResponseDto;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SuitableCropsService {

    private final SuitableCropsRepository suitableCropsRepository;
    private final SaleRepository saleRepository;
    private final SaleService saleService;
    private final CropService cropService;

    public Set<SuitableCrops> findAllBySaleId(Long saleId) {
        return suitableCropsRepository.findAllBySaleId(saleId);
    }

    public List<SuitableCropsResponseDto> getRecommendedCrops(Long saleId) {
        SaleDetailResponseDto saleList = saleService.findBySaleId(saleId); //거래유형, 가격, 면적, 토지유형 필터링 완료
        BigDecimal area = saleList.getSale().get(0).getArea() ; // saleId에 대한 면적

        Set<SuitableCrops> suitableCropsList = findAllBySaleId(saleId);

        //적합한 농작물 id 추출
        List<Long> cropIds = suitableCropsList.stream()
                .map(SuitableCrops::getCropId)
                .collect(Collectors.toList());

        //crop list가 나타남
        List<Crop> cropList = cropService.findAllByCropIdIn(cropIds);

        List<SuitableCropsResponseDto> dtoList = new ArrayList<>();

        for (Crop crop : cropList) {
            Double yield = crop.getHarvestAmount(); // 1평당 예상 수확량
            Double profit = crop.getProfit(); // 1평당 예상 수익

            if (yield != null && profit != null) {
                BigDecimal totalYield = area.multiply(BigDecimal.valueOf(yield)); // 총 예상 수확량
                BigDecimal totalProfit = area.multiply(BigDecimal.valueOf(profit)); // 총 예상 수익


                SuitableCropsResponseDto dto = new SuitableCropsResponseDto(
                        crop.getCropName(),  // cropName
                        totalYield,          // harvestAmount
                        totalProfit          // profit
                );
                dtoList.add(dto); // 리스트에 추가

            }
        }
        return dtoList;

    }

    // 매물별 대표작물, 최대 수익 계산
    public void calculateAndSaveProfitForSales(List<Sale> saleList) {
        for (Sale sale : saleList) {
            // 예상수익 계산 및 저장
//            if (sale.getProfit() == null || sale.getProfit().equals(BigDecimal.ZERO)) {
                List<SuitableCropsResponseDto> recommendedCrops = getRecommendedCrops(sale.getSaleId());

                Optional<SuitableCropsResponseDto> maxProfitCrop = recommendedCrops.stream()
                        .filter(dto -> dto.getProfit() != null)
                        .max(Comparator.comparing(SuitableCropsResponseDto::getProfit));

                // 최대 수익 작물 추출
                maxProfitCrop.ifPresent(dto -> {
                    sale.setProfit(dto.getProfit()); // 최대 수익을 sale에 설정
                    sale.setMainCrop(dto.getCropName()); // 대표 작물 insert
                });

                saleRepository.save(sale);
//            }
        }
    }

}
