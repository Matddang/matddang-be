package shop.matddang.matddangbe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import shop.matddang.matddangbe.user.client.GoogleClient;
import shop.matddang.matddangbe.global.redis.service.RefreshTokenRedisService;
import shop.matddang.matddangbe.security.jwt.JwtProvider;
import shop.matddang.matddangbe.user.client.KakaoResourceClient;
import shop.matddang.matddangbe.user.client.KakaoTokenClient;
import shop.matddang.matddangbe.user.converter.AuthConverter;
import shop.matddang.matddangbe.user.domain.UserEntity;
import shop.matddang.matddangbe.user.dto.request.TokenRequest;
import shop.matddang.matddangbe.user.dto.response.GoogleResourceServerResponse;
import shop.matddang.matddangbe.user.dto.response.KakaoResourceServerResponse;
import shop.matddang.matddangbe.user.dto.response.KakaoTokenResponse;
import shop.matddang.matddangbe.user.dto.response.SocialLoginResponse;
import shop.matddang.matddangbe.user.exception.AuthenticationErrorCode;
import shop.matddang.matddangbe.user.exception.AuthenticationException;
import shop.matddang.matddangbe.user.repository.UserRepository;

import static shop.matddang.matddangbe.security.constants.SecurityConstants.*;
import static shop.matddang.matddangbe.user.exception.AuthenticationErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final GoogleClient googleClient;
    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoResourceClient kakaoResourceClient;

    private final AuthConverter authConverter;

    private final RefreshTokenRedisService refreshTokenRedisService;
    @Value("${jwt.expiration.refresh}")
    private Long REFRESH_TOKEN_EXPIRE_TIME;

    @Value("${etc.kakao.client-id}")
    private String KAKAO_CLIENT_ID;
    @Value("${etc.kakao.redirect-uri}")
    private String KAKAO_REDIRECT_URI;



    @Transactional
    public SocialLoginResponse loginOrRegister(TokenRequest tokenRequest) {

        String bearerHeader = TOKEN_PREFIX + tokenRequest.accessToken();

        // 구글 리소스 서버에 access token으로 사용자 정보 요청
        GoogleResourceServerResponse googleUserInfo;
        try {
            googleUserInfo = googleClient.getUserInfo(bearerHeader);
            log.debug("구글 사용자 정보: {}", googleUserInfo);
        } catch (Exception e) {
            log.error("구글 사용자 정보 요청 실패: {}", e.getMessage());
            throw new AuthenticationException(GOOGLE_AUTHENTICATION_FAILED, e.getMessage());
        }

        UserEntity userEntity = getOrSave(googleUserInfo);

        return authConverter.toSocialLoginResponse(userEntity);
    }

    @Transactional
    public SocialLoginResponse loginOrRegisterKakao(String accessCode) {
        KakaoResourceServerResponse kakaoUserInfo = requestToKakao(accessCode);
        log.debug("카카오 사용자 정보: {}", kakaoUserInfo);

        UserEntity userEntity = getOrSave(kakaoUserInfo);

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

    private UserEntity getOrSave(KakaoResourceServerResponse serverResponse) {
        UserEntity userEntity = userRepository.findByEmail(serverResponse.kakaoAccount().email())
                .orElseGet(() -> UserEntity.from(serverResponse));
        return userRepository.save(userEntity);
    }

    private KakaoResourceServerResponse requestToKakao(String accessCode) {

        try {

            MultiValueMap<String, Object> formData = authConverter.toKakaoTokenRequest(accessCode, KAKAO_CLIENT_ID, KAKAO_REDIRECT_URI);
            KakaoTokenResponse kakaoToken = kakaoTokenClient.getKakaoToken(formData);
            log.debug("카카오 토큰 정보: {}", kakaoToken);

            String bearerHeader = TOKEN_PREFIX + kakaoToken.accessToken();
            return kakaoResourceClient.getUserInfo(bearerHeader);

        } catch (Exception e) {
            log.error("카카오 사용자 정보 요청 실패: {}", e.getMessage());
            throw new AuthenticationException(KAKAO_AUTHENTICATION_FAILED, e.getMessage());
        }
    }

}
