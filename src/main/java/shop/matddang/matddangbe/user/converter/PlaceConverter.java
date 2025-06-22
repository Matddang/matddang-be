package shop.matddang.matddangbe.user.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import shop.matddang.matddangbe.user.domain.MyPlace;
import shop.matddang.matddangbe.user.dto.response.PlaceResponse;

import static org.mapstruct.MappingConstants.ComponentModel.*;
import static org.mapstruct.ReportingPolicy.*;

@Mapper(
        componentModel = SPRING,
        unmappedTargetPolicy = IGNORE
)
public interface PlaceConverter {

    PlaceConverter INSTANCE = Mappers.getMapper(PlaceConverter.class);

    @Mapping(source = "id", target = "placeId")
    PlaceResponse toPlaceResponse(MyPlace myPlace);


}
