package shop.matddang.matddangbe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.matddang.matddangbe.user.converter.PlaceConverter;
import shop.matddang.matddangbe.user.domain.MyPlace;
import shop.matddang.matddangbe.user.domain.UserEntity;
import shop.matddang.matddangbe.user.dto.request.PlaceUpdateRequest;
import shop.matddang.matddangbe.user.dto.response.PlaceResponse;
import shop.matddang.matddangbe.user.exception.PlaceErrorCode;
import shop.matddang.matddangbe.user.exception.PlaceException;
import shop.matddang.matddangbe.user.repository.PlaceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PlaceService {

    private final UserService userService;
    private final PlaceRepository placeRepository;
    private final PlaceConverter placeConverter;


    public List<PlaceResponse> getMyPlaces(String userId) {
        UserEntity user = userService.findUser(userId);
        List<MyPlace> myPlaces = placeRepository.findAllByUser_Id(Long.parseLong(userId));

        return myPlaces.stream()
                .map(placeConverter::toPlaceResponse)
                .toList();
    }

    @Transactional
    public PlaceResponse addPlace(PlaceUpdateRequest placeUpdateRequest, String userId) {

        UserEntity user = userService.findUser(userId);

        MyPlace myPlace = MyPlace.of(user, placeUpdateRequest);
        placeRepository.save(myPlace);

        return placeConverter.toPlaceResponse(myPlace);
    }


    @Transactional
    public PlaceResponse updatePlace(PlaceUpdateRequest placeUpdateRequest, String userId, Long placeId) {

        UserEntity user = userService.findUser(userId);
        MyPlace myPlace = findPlaceById(placeId);

        checkMyPlaceAuth(placeId, myPlace, user);

        myPlace.update(placeUpdateRequest);
        return placeConverter.toPlaceResponse(myPlace);

    }


    @Transactional
    public void deletePlace(Long placeId, String userId) {
        UserEntity user = userService.findUser(userId);
        MyPlace myPlace = findPlaceById(placeId);

        checkMyPlaceAuth(placeId, myPlace, user);
        placeRepository.delete(myPlace);
    }

    private void checkMyPlaceAuth(Long placeId, MyPlace myPlace, UserEntity user) {
        if (!myPlace.getUser().getId().equals(user.getId())) {
            throw new PlaceException(PlaceErrorCode.UNAUTHORIZED_ACCESS, "해당 장소에 대한 권한이 없습니다: " + placeId);
        }
    }

    private MyPlace findPlaceById(Long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceException(PlaceErrorCode.PLACE_NOT_FOUND, "해당 장소 id로 찾을 수 없습니다: {}" + placeId));
    }




}
