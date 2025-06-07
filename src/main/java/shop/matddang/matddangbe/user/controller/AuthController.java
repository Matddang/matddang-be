package shop.matddang.matddangbe.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.matddang.matddangbe.user.dto.request.TokenRequest;
import shop.matddang.matddangbe.user.dto.response.SocialLoginResponse;
import shop.matddang.matddangbe.user.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/google")
    public ResponseEntity<SocialLoginResponse> googleLogin (
            @Valid @RequestBody TokenRequest tokenRequest) {

        SocialLoginResponse socialLoginResponse = authService.loginOrRegister(tokenRequest);
        String accessWithBearer = authService.createAccessTokenWhenLogin(socialLoginResponse.userId());

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, accessWithBearer)
                .body(socialLoginResponse);

    }

}
