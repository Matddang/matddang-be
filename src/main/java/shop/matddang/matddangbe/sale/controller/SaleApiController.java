package shop.matddang.matddangbe.sale.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.suitableCrops.dto.SuitableCropsResponseDto;
import shop.matddang.matddangbe.suitableCrops.domain.SuitableCrops;
import shop.matddang.matddangbe.sale.dto.SaleRequestDto;
import shop.matddang.matddangbe.sale.service.SaleService;
import shop.matddang.matddangbe.suitableCrops.service.SuitableCropsService;

import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "sale", description = "매물 로드")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleApiController {

    private final SaleService saleService;
    private final SuitableCropsService suitableCropsService;

    //매물 조회 & 검색
    @Operation(summary = "매물 조회 & 검색",
            description = "매물 조회 & 검색 & 필터링")
    @PostMapping
    public ResponseEntity<List<Sale>> getSales(@RequestBody SaleRequestDto requestDto) {

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

        /**
        // 주소 기반 위도, 경도 추출
        geoApiService.LocationSaveService(saleList);
        // 매물별 대표작물, 최대 수익 계산 로직 (하나씩만)
        suitableCropsService.calculateAndSaveProfitForSales(saleList);
        **/

        return ResponseEntity.ok(saleList);

    }

    //매물 상세의 적합농산물 예상 수확량 및 수익 (3개 추출)
    @Operation(summary = "적합농산물", description = "예상 수확량 & 예상수익")
    @GetMapping("/recommend/{saleId}")
    public ResponseEntity<List<SuitableCropsResponseDto>> getRecommendCrops(@PathVariable("saleId") Long saleId) {
        List<SuitableCropsResponseDto> dtoList = suitableCropsService.getRecommendedCrops(saleId);
        return ResponseEntity.ok(dtoList);
    }

}
