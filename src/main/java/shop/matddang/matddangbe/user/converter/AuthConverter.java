package shop.matddang.matddangbe.user.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import shop.matddang.matddangbe.user.domain.UserEntity;
import shop.matddang.matddangbe.user.dto.response.GoogleResourceServerResponse;
import shop.matddang.matddangbe.user.dto.response.SocialLoginResponse;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = IGNORE
)
public interface AuthConverter {

    AuthConverter INSTANCE = Mappers.getMapper(AuthConverter.class);

    @Mapping(source = "id", target = "userId")
    SocialLoginResponse toSocialLoginResponse(UserEntity userEntity);

    default String map(Long value) {
        return value != null ? String.valueOf(value) : null;
    }


    default MultiValueMap<String, Object> toKakaoTokenRequest(String accessCode, String kakaoClientId, String kakaoRedirectUri) {
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", kakaoClientId);
        formData.add("redirect_uri", kakaoRedirectUri);
        formData.add("code", accessCode);
        formData.add("client_secret", "");
        return formData;
    }

}
