package shop.matddang.matddangbe.sale.dto;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleCompareResponseDto {

    private SaleDetailResponseDto sale1;
    private SaleDetailResponseDto sale2;
    private LocalDate compareTime;

}
