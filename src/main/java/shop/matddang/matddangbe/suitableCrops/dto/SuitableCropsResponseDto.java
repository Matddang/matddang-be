package shop.matddang.matddangbe.suitableCrops.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class SuitableCropsResponseDto {

    private String cropName; //농산물명
    private BigDecimal harvestAmount; //예상수확량
    private BigDecimal profit; //수익

}
