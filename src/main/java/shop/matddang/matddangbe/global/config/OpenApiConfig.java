package shop.matddang.matddangbe.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.matddang.matddangbe.global.dto.CommonResponse;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI openAPI() {
        String jwtSchemeName = "JWT";

        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                );

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);


        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .addServersItem(new Server().url("/"))
                .info(apiInfo());
    }

    private io.swagger.v3.oas.models.info.Info apiInfo() {
        return new io.swagger.v3.oas.models.info.Info()
                .title("Matddang API")
                .version("v1")
                .description("Matddang Backend API 문서");
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            addResponseBodyWrapperSchemaExample(operation, CommonResponse.class, "data");
            return operation;
        };
    }

    private void addResponseBodyWrapperSchemaExample(Operation operation, Class<?> type, String wrapFieldName) {
        ApiResponse response200 = operation.getResponses().get("200");
        if (response200 != null) {
            final Content content = response200.getContent();
            if (content != null) {
                content.keySet().forEach(mediaTypeKey -> {
                    final MediaType mediaType = content.get(mediaTypeKey);
                    mediaType.schema(wrapSchema(mediaType.getSchema(), type, wrapFieldName));
                });
            }
        }
    }

    @SneakyThrows
    private <T> Schema<T> wrapSchema(Schema<?> originalSchema, Class<T> type, String wrapFieldName) {
        final Schema<T> wrapperSchema = new Schema<>();

        // CommonResponse의 기본 필드 추가 (status = 200, message = "OK")
        wrapperSchema.addProperty("status", new Schema<Integer>().example(200));
        wrapperSchema.addProperty("message", new Schema<String>().example("OK"));

        // 원래 응답 스키마를 "data" 필드에 추가
        wrapperSchema.addProperty(wrapFieldName, originalSchema);

        return wrapperSchema;
    }
}