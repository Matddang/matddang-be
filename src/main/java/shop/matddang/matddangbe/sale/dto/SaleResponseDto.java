package shop.matddang.matddangbe.sale.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleResponseDto {

    private Long saleId;
    private String saleCategory; // 임대, 매매
    private String landType; // 농지, 주택
    private String saleAddr; //주소
    private Double wgsX; //x좌표
    private Double wgsY; //Y좌표
    private String landCategory; //전_전, 답_답
    private BigDecimal price; // 매매금액
    private BigDecimal officialPrice; //공지시가
    private LocalDateTime regDate; //등록일시
    private BigDecimal area; //면적

    private List<Long> cropIds; //적합농산물
    private BigDecimal Profit; //예상수익

    private String imageUrl; // 이미지 URL

}
