package shop.matddang.matddangbe.user.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
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
}
