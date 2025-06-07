package shop.matddang.matddangbe.user.dto.request;

public record TokenRequest(
        String accessToken,
        String idToken
) {
}
