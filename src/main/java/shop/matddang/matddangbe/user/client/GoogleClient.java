package shop.matddang.matddangbe.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import shop.matddang.matddangbe.user.dto.response.GoogleResourceServerResponse;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@FeignClient(
        name = "google-oauth-client",
        url  = "${etc.google-profile-url}"
)
public interface GoogleClient {


    @GetMapping
    GoogleResourceServerResponse getUserInfo(
            @RequestHeader(AUTHORIZATION) String authorizationHeader
    );

}
