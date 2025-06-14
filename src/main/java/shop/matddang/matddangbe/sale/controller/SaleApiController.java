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
import shop.matddang.matddangbe.sale.dto.SaleRequestDto;
import shop.matddang.matddangbe.sale.service.SaleService;

import java.util.List;

@Tag(name="sale", description = "매물 로드")
@RequiredArgsConstructor
@RestController
@RequestMapping("/sale")
public class SaleApiController {

    private final SaleService saleService;


    //매물 조회 & 검색
    @Operation(summary = "매물 조회 & 검색",
            description = "매물 조회 & 검색 & 필터링")
    @PostMapping
    public ResponseEntity<CommonResponse<List<Sale>>> getSales(@RequestBody SaleRequestDto requestDto) {

        List<Sale> saleList = saleService.searchSales(requestDto);

        //TODO
        /**
         * 적합 농산물을 빼고 필터링 진행,
         * 그 결과의 매물과 적합한 농산물 매칭하는 table 하나 만들고
         * 포함되는 것 중복 제거해서 매물 리스트 가져옴
         * 그리고 sales에 findbyid로 리스트 추출.
         */

        return ResponseEntity.ok(
                CommonResponse.<List<Sale>>builder()
                        .status(HttpStatus.OK.value())
                        .message("매물 조회 성공")
                        .data(saleList)
                        .build()
        );

    }

}
