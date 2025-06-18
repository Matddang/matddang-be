package shop.matddang.matddangbe.Liked.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.Liked.domain.Liked;
import shop.matddang.matddangbe.Liked.dto.LikedResponseDto;
import shop.matddang.matddangbe.Liked.repository.LikedRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikedService {

    private final LikedRepository likedRepository;

    // 매물 좋아요 유무 확인
    public List<Liked> getLikedSaleList(Long saleId, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인 상태가 아닙니다.");
        }

        List<Liked> likedSaleList = likedRepository.findByUserId(userId);
        return likedSaleList;
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



