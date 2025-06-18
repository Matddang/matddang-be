package shop.matddang.matddangbe.Liked.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor //생성자
@Data
public class LikedResponseDto {

    private Long userId;
    private Long saleId;
    private String liked;

}
