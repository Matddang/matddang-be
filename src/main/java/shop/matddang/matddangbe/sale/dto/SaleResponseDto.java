package shop.matddang.matddangbe.sale.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.matddang.matddangbe.sale.domain.Sale;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponseDto {

    //Todo 나중에 개발 예정
    private List<Sale> sale; // sale 정보
    private String imageUrl; // 이미지 URL

}
