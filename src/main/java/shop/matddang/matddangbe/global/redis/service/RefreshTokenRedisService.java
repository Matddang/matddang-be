package shop.matddang.matddangbe.global.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.global.redis.entity.RefreshToken;
import shop.matddang.matddangbe.global.redis.repository.RefreshTokenRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String userId, String refreshToken, Long ttl) {

        RefreshToken token = RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .ttl(ttl)
                .build();

        refreshTokenRepository.save(token);
    }

    public Optional<RefreshToken> findRefreshToken(String userId) {
        return refreshTokenRepository.findById(userId);
    }

    public void deleteRefreshToken(String userId) {
        refreshTokenRepository.deleteById(userId);
    }

}
