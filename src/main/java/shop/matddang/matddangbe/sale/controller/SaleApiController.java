package shop.matddang.matddangbe.sale.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.matddang.matddangbe.global.dto.CommonResponse;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.domain.SuitableCrops;
import shop.matddang.matddangbe.sale.dto.SaleRequestDto;
import shop.matddang.matddangbe.sale.service.GeoApiService;
import shop.matddang.matddangbe.sale.service.SaleService;

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

        /** // 위도 경도 값 추출을 위한 코드 -> 현재는 필요X
         **/
        // 각 매물에 대해 주소로 BCD 코드 조회 및 DB에 저장
        for (Sale sale : saleList) {
//            if (sale.getWgsX() == null || sale.getWgsX() == 0L) {
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
//            }
        }



        return ResponseEntity.ok(
                CommonResponse.<List<Sale>>builder()
                        .status(HttpStatus.OK.value())
                        .message("매물 조회 성공")
                        .data(saleList)
                        .build()
        );

    }

}
