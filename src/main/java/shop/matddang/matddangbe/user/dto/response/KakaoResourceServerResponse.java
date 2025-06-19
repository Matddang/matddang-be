package shop.matddang.matddangbe.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record KakaoResourceServerResponse(
        Long id,
        @JsonProperty("kakao_account")
        @NotBlank
        KakaoAccount kakaoAccount,
        @NotBlank
        KakaoProperties properties
) {
    public record KakaoAccount(
            @JsonProperty("has_email")
            Boolean hasEmail,
            @JsonProperty("email_needs_agreement")
            Boolean emailNeedsAgreement,
            @JsonProperty("is_email_valid")
            Boolean isEmailValid,
            @JsonProperty("is_email_verified")
            Boolean isEmailVerified,
            String email
    ) {}

    public record KakaoProperties(
            String nickname,
            @JsonProperty("profile_image") String profileImage,
            @JsonProperty("thumbnail_image") String thumbnailImage
    ) {}
}
