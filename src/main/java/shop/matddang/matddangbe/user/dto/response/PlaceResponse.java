package shop.matddang.matddangbe.user.dto.response;

import shop.matddang.matddangbe.user.domain.enums.PlaceType;

public record PlaceResponse(
        Long placeId,
        PlaceType placeType,
        String placeName,
        String address,
        Double latitude,
        Double longitude
) {
}
