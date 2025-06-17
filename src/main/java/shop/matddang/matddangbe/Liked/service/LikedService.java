package shop.matddang.matddangbe.Liked.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.Liked.domain.Liked;
import shop.matddang.matddangbe.Liked.repository.LikedRepository;
import shop.matddang.matddangbe.user.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikedService {

    private final LikedRepository LikedRepository;

    // 매물 좋아요 유무 확인
    public boolean saleIsLiked(Long saleId, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인 상태가 아닙니다.");
        }

        Optional<Liked> userLiked = LikedRepository.findByUserIdAndSaleId(userId, saleId);
        return userLiked.isPresent();
    }

}



