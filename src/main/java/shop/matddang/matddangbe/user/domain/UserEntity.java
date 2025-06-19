package shop.matddang.matddangbe.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import shop.matddang.matddangbe.global.domain.BaseTimeEntityWithDeletion;
import shop.matddang.matddangbe.user.dto.response.GoogleResourceServerResponse;
import shop.matddang.matddangbe.user.dto.response.KakaoResourceServerResponse;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static shop.matddang.matddangbe.user.domain.Role.USER;

@Entity
@Table(name = "`user`")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class UserEntity extends BaseTimeEntityWithDeletion {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String email;

    // 실명
    @Column(length = 30)
    private String name;

    @Column(length = 1024)
    private String imageUrl;

    @Column(nullable = true, unique = true)
    private String nickName;

    private boolean enabled;

    @Enumerated(STRING)
    private Role role;

    @Enumerated(STRING)
    SocialLoginType socialLoginType = SocialLoginType.NONE;

    /**
     * UserEntity 생성 메서드
     * 회원 가입시 사용
     */
    public static UserEntity from(GoogleResourceServerResponse serverResponse) {
        return UserEntity.builder()
                .email(serverResponse.email())
                .name(serverResponse.name())
                .imageUrl(serverResponse.picture())

                .socialLoginType(SocialLoginType.GOOGLE)
                .role(Role.USER)
                .enabled(true)
                .build();
    }

    public static UserEntity from(KakaoResourceServerResponse serverResponse) {
        return UserEntity.builder()
                .email(serverResponse.kakaoAccount().email())
                .name(serverResponse.properties().nickname())
                .imageUrl(serverResponse.properties().profileImage())
                .nickName(serverResponse.properties().nickname())

                .socialLoginType(SocialLoginType.KAKAO)
                .role(Role.USER)
                .enabled(true)
                .build();
    }

    @Builder(access = PRIVATE)
    private UserEntity(String email, String name, String nickName, boolean enabled, Role role, String imageUrl, SocialLoginType socialLoginType) {

        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.enabled = enabled;
        this.role = role;
        this.imageUrl = imageUrl;
        this.socialLoginType = socialLoginType;

    }
}
