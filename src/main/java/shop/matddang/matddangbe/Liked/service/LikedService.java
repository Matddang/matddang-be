package shop.matddang.matddangbe.Liked.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.Liked.domain.Liked;
import shop.matddang.matddangbe.Liked.dto.LikedResponseDto;
import shop.matddang.matddangbe.Liked.repository.LikedRepository;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.dto.SimilarSaleDto;
import shop.matddang.matddangbe.sale.repository.SaleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikedService {

    private final LikedRepository likedRepository;
    private final SaleRepository saleRepository;

    // 매물 좋아요 list
    public List<SimilarSaleDto> getLikedSaleList(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인 상태가 아닙니다.");
        }

        List<Liked> likedSaleList = likedRepository.findByUserId(userId);

        List<Long> likedSaleIds = likedSaleList.stream()
                .map(Liked::getSaleId)
                .collect(Collectors.toList());

        List<Sale> likedSales = saleRepository.findBySaleIdIn(likedSaleIds);

        // 4. DTO 변환
        List<SimilarSaleDto> similarSales = likedSales.stream()
                .map(s -> {
                    SimilarSaleDto dto = new SimilarSaleDto();
                    dto.setSaleId(s.getSaleId());
                    dto.setSaleCategory(s.getSaleCategory());
                    dto.setLandType(s.getLandType());
                    dto.setSaleAddr(s.getSaleAddr());
                    dto.setLandCategory(s.getLandCategory());
                    dto.setPrice(s.getPrice());
                    dto.setArea(s.getArea());
                    dto.setMainCrop(s.getMainCrop());
                    dto.setImgUrl("https://matddang.s3.ap-northeast-2.amazonaws.com/saleimgs/" + s.getSaleId() + ".png");
                    return dto;
                })
                .collect(Collectors.toList());

        return similarSales;
    }

    public boolean userLiked(Long saleId, Long userId) {
        Optional<Liked> existing = likedRepository.findByUserIdAndSaleId(userId, saleId);
        return existing.isPresent();
    }

    public LikedResponseDto toggleSaleLike(Long saleId, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인 상태가 아닙니다.");
        }


        Optional<Liked> existing = likedRepository.findByUserIdAndSaleId(userId, saleId);

        if (existing.isPresent()) {
            likedRepository.delete(existing.get());
            return new LikedResponseDto(userId, saleId, "UNLIKED"); // 좋아요 취소됨
        } else {
            Liked liked = Liked.builder()
                    .userId(userId)
                    .saleId(saleId)
                    .build();
            likedRepository.save(liked);
            return new LikedResponseDto(userId, saleId, "LIKED"); // 좋아요 등록됨
        }

    }


}



