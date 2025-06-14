package shop.matddang.matddangbe.sale.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.matddang.matddangbe.crop.domain.Crop;
import shop.matddang.matddangbe.crop.service.CropService;
import shop.matddang.matddangbe.global.dto.CommonResponse;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.suitableCrops.dto.SuitableCropsResponseDto;
import shop.matddang.matddangbe.suitableCrops.domain.SuitableCrops;
import shop.matddang.matddangbe.sale.dto.SaleRequestDto;
import shop.matddang.matddangbe.sale.service.GeoApiService;
import shop.matddang.matddangbe.sale.service.SaleService;
import shop.matddang.matddangbe.suitableCrops.service.SuitableCropsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Tag(name = "sale", description = "매물 로드")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleApiController {

    private final SaleService saleService;
    private final GeoApiService geoApiService;  // 좌표값 추출
    private final SuitableCropsService suitableCropsService;
    private final CropService cropService;

    //매물 조회 & 검색
    @Operation(summary = "매물 조회 & 검색",
            description = "매물 조회 & 검색 & 필터링")
    @PostMapping
    public ResponseEntity<CommonResponse<List<Sale>>> getSales(@RequestBody SaleRequestDto requestDto) {


        List<Sale> saleList = saleService.searchSales(requestDto); //거래유형, 가격, 면적, 토지유형 필터링 완료

        // 농작물 필터링
        if (!requestDto.getCropIds().isEmpty()) {

            List<Long> saleIds = saleList.stream()
                    .map(Sale::getSaleId)
                    .collect(Collectors.toList());

            List<SuitableCrops> saleFilterList = saleService.findBySaleIdInAndCropIdIn(saleIds, requestDto.getCropIds());

            Set<Long> saleIdSet = new HashSet<>();
            List<SuitableCrops> uniqueSaleList = saleFilterList.stream()
                    .filter(s -> saleIdSet.add(s.getSaleId()))  // 중복 제거
                    .collect(Collectors.toList());

            saleList = saleService.findAllById(saleIdSet);

        }

        /** // -------------------위도 경도 값 추출을 위한 코드 -> 현재는 필요X----------------------
        // 각 매물에 대해 주소로 BCD 코드 조회 및 DB에 저장
        for (Sale sale : saleList) {
           if (sale.getWgsX() == null || sale.getWgsX() == 0L) {
                Double[] coords = geoApiService.getGeoCoordFromAddress(sale.getSaleAddr());

                if (coords != null && coords.length == 2) {
                    sale.setWgsX(coords[0]);
                    sale.setWgsY(coords[1]);
                } else {
                    // 예외 처리로 0으로 설정
                    sale.setWgsX((double) 0);
                    sale.setWgsY((double) 0);
                }
                    saleService.save(sale);  // BCD 코드가 포함된 Sale 객체를 DB에 저장
            }
        }
         // ----------------------------------------------------------------------
        **/
        return ResponseEntity.ok(
                CommonResponse.<List<Sale>>builder()
                        .status(HttpStatus.OK.value())
                        .message("매물 조회 성공")
                        .data(saleList)
                        .build()
        );

    }

    //매물 상세의 적합농산물 예상 수확량 및 수익
    @Operation(summary = "적합농산물", description = "예상 수확량 & 예상수익")
    @GetMapping("/recommend/{saleId}")
    public ResponseEntity<CommonResponse<List<SuitableCropsResponseDto>>> getRecommendCrops(@PathVariable("saleId") Long saleId) {

        List<Sale> saleList = saleService.findBySaleId(saleId); //거래유형, 가격, 면적, 토지유형 필터링 완료
        BigDecimal area = saleList.get(0).getArea(); // saleId에 대한 면적

        Set<SuitableCrops> suitableCropsList = suitableCropsService.findAllBySaleId(saleId);

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

        return ResponseEntity.ok(
                CommonResponse.<List<SuitableCropsResponseDto>>builder()
                        .status(HttpStatus.OK.value())
                        .message("적합 농산물 조회 성공")
                        .data(dtoList)
                        .build()
        );

    }

}
