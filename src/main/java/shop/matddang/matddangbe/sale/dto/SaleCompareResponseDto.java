package shop.matddang.matddangbe.sale.dto;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleCompareResponseDto {

    private Long saleId1;
    private Long saleId2;

    private LocalDate compareTime;

}
