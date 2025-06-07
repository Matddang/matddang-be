package shop.matddang.matddangbe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.matddang.matddangbe.client.GoogleClient;
import shop.matddang.matddangbe.global.redis.service.RefreshTokenRedisService;
import shop.matddang.matddangbe.security.constants.SecurityConstants;
import shop.matddang.matddangbe.security.jwt.JwtProvider;
import shop.matddang.matddangbe.user.converter.AuthConverter;
import shop.matddang.matddangbe.user.domain.UserEntity;
import shop.matddang.matddangbe.user.dto.request.TokenRequest;
import shop.matddang.matddangbe.user.dto.response.GoogleResourceServerResponse;
import shop.matddang.matddangbe.user.dto.response.SocialLoginResponse;
import shop.matddang.matddangbe.user.repository.UserRepository;

import static shop.matddang.matddangbe.security.constants.SecurityConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final GoogleClient googleClient;

    private final AuthConverter authConverter;

    private final RefreshTokenRedisService refreshTokenRedisService;
    @Value("${jwt.expiration.refresh}")
    private Long REFRESH_TOKEN_EXPIRE_TIME;


    @Transactional
    public SocialLoginResponse loginOrRegister(TokenRequest tokenRequest) {

        String bearerHeader = TOKEN_PREFIX + tokenRequest.accessToken();

        // 구글 리소스 서버에 access token으로 사용자 정보 요청
        GoogleResourceServerResponse googleUserInfo = googleClient.getUserInfo(bearerHeader);
        log.debug("구글 사용자 정보: {}", googleUserInfo);

        UserEntity userEntity = getOrSave(googleUserInfo);

        return authConverter.toSocialLoginResponse(userEntity);
    }


    public String createAccessTokenWhenLogin(String userId) {

        Authentication authentication = jwtProvider.getAuthenticationFromUserId(userId);
        String accessToken = jwtProvider.generateAccessToken(authentication, userId);
        String refreshToken = jwtProvider.generateRefreshToken(authentication, userId);

        refreshTokenRedisService.saveRefreshToken(userId, refreshToken, REFRESH_TOKEN_EXPIRE_TIME);

        return TOKEN_PREFIX + accessToken;
    }


    private UserEntity getOrSave(GoogleResourceServerResponse serverResponse) {
        UserEntity userEntity = userRepository.findByEmail(serverResponse.email())
                .orElseGet(() -> UserEntity.from(serverResponse));
        return userRepository.save(userEntity);
    }

}
