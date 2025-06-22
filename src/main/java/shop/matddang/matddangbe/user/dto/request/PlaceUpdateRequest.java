package shop.matddang.matddangbe.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import shop.matddang.matddangbe.user.domain.enums.PlaceType;

public record PlaceUpdateRequest(
        @NotNull
        PlaceType placeType,
        @NotBlank
        String placeName,
        @NotBlank
        String address
) {
}
