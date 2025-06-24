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


    private List<String> saleCategoryList; // [임대, 매매]
    private List<String> landCategoryList; // [전_전, 답_답, 과수원]
    private List<Long> cropIds; //[1,2,3,4]
    private List<Double> location; //[155.496327, 128.48978]
    private List<Double> zoom; // [34.425146852, 127.10757514, 34.695146852, 127.40757514]

    private String sortBy; // profit, liked, both
    private String keyword;

    // 기본값을 지정
    private int page = 1;
    private int size = 257;
    private BigDecimal minPrice = BigDecimal.valueOf(0); // 최소 가격
    private BigDecimal maxPrice = BigDecimal.valueOf(1000000000); // 최대 가격
    private BigDecimal minArea= BigDecimal.valueOf(0); // 최소 면적
    private BigDecimal maxArea= BigDecimal.valueOf(1000000000); // 최대 면적


}
