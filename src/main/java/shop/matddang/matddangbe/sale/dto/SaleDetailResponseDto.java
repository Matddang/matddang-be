package shop.matddang.matddangbe.sale.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.matddang.matddangbe.sale.domain.Sale;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetailResponseDto {


    private List<Sale> sale; // sale 정보
    private List<SimilarSaleDto> similarSales ; //비슷한 매물 리스트

}
