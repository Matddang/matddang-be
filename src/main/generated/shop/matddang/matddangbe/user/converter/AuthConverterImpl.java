package shop.matddang.matddangbe.user.converter;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import shop.matddang.matddangbe.user.domain.UserEntity;
import shop.matddang.matddangbe.user.dto.response.SocialLoginResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T04:10:55+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.13 (Oracle Corporation)"
)
@Component
public class AuthConverterImpl implements AuthConverter {

    @Override
    public SocialLoginResponse toSocialLoginResponse(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        String userId = null;
        String email = null;
        String name = null;
        boolean enabled = false;

        userId = map( userEntity.getId() );
        email = userEntity.getEmail();
        name = userEntity.getName();
        enabled = userEntity.isEnabled();

        SocialLoginResponse socialLoginResponse = new SocialLoginResponse( userId, email, name, enabled );

        return socialLoginResponse;
    }
}
