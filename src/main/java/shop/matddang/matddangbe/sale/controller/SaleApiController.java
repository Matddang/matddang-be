package shop.matddang.matddangbe.sale.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import shop.matddang.matddangbe.Liked.domain.Liked;
import shop.matddang.matddangbe.Liked.dto.LikedResponseDto;
import shop.matddang.matddangbe.Liked.service.LikedService;
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
    private final LikedService likedService;

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


        if (!requestDto.getLocation().isEmpty()){
            // 지정 장소 기반 거리 정렬 필터
            saleList = saleService.getsearchSalesByLocation(requestDto.getLocation(),saleList); //거래유형, 가격, 면적, 토지유형 필터링 완료
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

    //매물 ID별 상세 정보 로드
    @Operation(summary = "매물 ID별 상세 정보",
            description = "매물 ID별 상세 정보")
    @GetMapping("/{saleId}")
    public ResponseEntity<List<Sale>> getSalesDetail(@PathVariable("saleId") Long saleId) {

        List<Sale> saleList = saleService.findBySaleId(saleId);
        return ResponseEntity.ok(saleList);

    }

    @Operation(summary = "유저별 매물의 좋아요")
    @GetMapping("/is-liked/{saleId}")
    public ResponseEntity<Object> getSaleIsLiked(@PathVariable("saleId") Long saleId, @AuthenticationPrincipal User currentUser) {

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인된 사용자만 접근할 수 있습니다.");
        }

        Long userId = Long.parseLong(currentUser.getUsername());
        List<Liked> likedSaleList = likedService.getLikedSaleList(saleId, userId);
        return ResponseEntity.ok(likedSaleList);

    }

    @Operation(summary = "매물 좋아요/취소")
    @GetMapping("/liked/{saleId}")
    public ResponseEntity<Object> saleLiked(@PathVariable("saleId") Long saleId, @AuthenticationPrincipal User currentUser) {

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("로그인된 사용자만 접근할 수 있습니다.");
        }

        Long userId = Long.parseLong(currentUser.getUsername());

        LikedResponseDto responseDto = likedService.toggleSaleLike(saleId, userId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "지역/지번 기반 매물 검색")
    @GetMapping("/search/address")
    public ResponseEntity<Object> getSaleListSearchByAddr(@RequestParam("keyword") String keyword, @AuthenticationPrincipal User currentUser) {

        List<Sale> saleList = saleService.findBySaleAddrLike(keyword);

        if ( currentUser !=null ){
            Long userId = Long.parseLong(currentUser.getUsername());
            saleService.saveSearchKeyword(userId,keyword);
        }

        return ResponseEntity.ok().body(saleList);
    }

}
