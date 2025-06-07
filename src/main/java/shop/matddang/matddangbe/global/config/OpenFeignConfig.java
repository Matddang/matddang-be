package shop.matddang.matddangbe.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("shop.matddang")
public class OpenFeignConfig {
}
