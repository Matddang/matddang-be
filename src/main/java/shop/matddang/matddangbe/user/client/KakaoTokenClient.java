package shop.matddang.matddangbe.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shop.matddang.matddangbe.user.dto.response.KakaoTokenResponse;

@FeignClient(
        name = "kakao-token-client",
        url  = "${etc.kakao-token-url}",
        configuration = shop.matddang.matddangbe.global.config.OpenFeignFormConfig.class
)
public interface KakaoTokenClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoTokenResponse getKakaoToken(
            @RequestBody MultiValueMap<String, Object> formData
    );

}
