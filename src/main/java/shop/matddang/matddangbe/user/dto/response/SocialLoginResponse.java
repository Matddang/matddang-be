package shop.matddang.matddangbe.user.dto.response;


public record SocialLoginResponse(
        String userId,
        String email,
        String name,
        boolean enabled
) {
}
