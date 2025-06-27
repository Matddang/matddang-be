package shop.matddang.matddangbe.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import shop.matddang.matddangbe.user.domain.enums.PlaceType;

public record PlaceUpdateRequest(
        @NotNull
        @Schema(
                description = "장소 타입",
                example = "HOME, FARM"
        )
        PlaceType placeType,
        @NotBlank
        String placeName,
        @NotBlank
        String address,
        @NotNull
        Double latitude,
        @NotNull
        Double longitude

) {
}
