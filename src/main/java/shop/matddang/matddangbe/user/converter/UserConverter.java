package shop.matddang.matddangbe.user.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.matddang.matddangbe.user.domain.UserEntity;
import shop.matddang.matddangbe.user.dto.response.UserDetailResponse;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.*;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = IGNORE
)
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserDetailResponse toUserDetailResponse(UserEntity user);
}
