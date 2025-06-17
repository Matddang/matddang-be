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
    private final UserRepository userRepository;

    // 아티클 스크랩 유무 확인
    public boolean SalesLiked(Long saleId, String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("로그인 상태가 아닙니다.");
        }

//        userRepository.findById(userId).orElseThrow(() ->
//                new IllegalArgumentException("해당 userId의 user 찾을 수 없음 id: " + userId)
//        );


        Optional<Liked> likeScrap = LikedRepository.findByUserIdAndSaleId(userId, saleId);
        return likeScrap.isPresent();
    }



}
