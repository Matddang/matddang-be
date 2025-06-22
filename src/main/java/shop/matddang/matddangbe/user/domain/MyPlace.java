package shop.matddang.matddangbe.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.matddang.matddangbe.user.domain.enums.PlaceType;
import shop.matddang.matddangbe.user.dto.request.PlaceUpdateRequest;

@Entity
@Table(name = "my_place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MyPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private PlaceType placeType;

    private String placeName;

    private String address;


    public static MyPlace of(UserEntity user, PlaceUpdateRequest placeUpdateRequest) {
        return MyPlace.builder()
                .user(user)
                .placeType(placeUpdateRequest.placeType())
                .placeName(placeUpdateRequest.placeName())
                .address(placeUpdateRequest.address())
                .build();
    }

    @Builder
    public MyPlace(UserEntity user, PlaceType placeType, String placeName, String address) {
        this.user = user;
        this.placeType = placeType;
        this.placeName = placeName;
        this.address = address;
    }


    public void update(PlaceUpdateRequest placeUpdateRequest) {
        this.placeType = placeUpdateRequest.placeType();
        this.placeName = placeUpdateRequest.placeName();
        this.address = placeUpdateRequest.address();
    }
}
