package shop.matddang.matddangbe.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.matddang.matddangbe.user.dto.response.KakaoResourceServerResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(
        name = "kakao-oauth-client",
        url  = "${etc.kakao-profile-url}"
)
public interface KakaoResourceClient {

    @GetMapping
    KakaoResourceServerResponse getUserInfo(
            @RequestHeader(AUTHORIZATION) String authorizationHeader
    );

}
