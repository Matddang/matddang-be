package shop.matddang.matddangbe.sale.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 필터링을 위해 매물 검색 조건을 받아오는 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleRequestDto {

    private String buy;
    private String rent;

    private BigDecimal minPrice; // 최소 가격
    private BigDecimal maxPrice; // 최대 가격

    private BigDecimal minArea; // 최소 면적
    private BigDecimal maxArea; // 최대 면적

    private String landCategory_d; // 답
    private String landCategory_j; // 전
    private String landCategory_g; // 과수원

    private Long crop1;
    private Long crop2;
    private Long crop3;
    private Long crop4;


}
