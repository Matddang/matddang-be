package shop.matddang.matddangbe.sale.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimilarSaleDto {

    // 비슷한 매물 리스트 제공하기 위한 DTO
    private Long saleId;
    private String saleCategory;
    private String landType;
    private String saleAddr;
    private String landCategory;
    private BigDecimal price;
    private BigDecimal area;
    private String mainCrop;
    private String imgUrl;

}
