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

    private int page;
    private int size = 10;

    private List<String> saleCategoryList; // [임대, 매매]

    private BigDecimal minPrice; // 최소 가격
    private BigDecimal maxPrice; // 최대 가격

    private BigDecimal minArea; // 최소 면적
    private BigDecimal maxArea; // 최대 면적

    private List<String> landCategoryList; // [전_전, 답_답, 과수원]
    private List<Long> cropIds; //[1,2,3,4]

    private List<Double> location; //[155.496327, 128.48978]

}
