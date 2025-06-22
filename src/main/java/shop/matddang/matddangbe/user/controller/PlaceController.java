package shop.matddang.matddangbe.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import shop.matddang.matddangbe.user.dto.request.PlaceUpdateRequest;
import shop.matddang.matddangbe.user.dto.response.PlaceResponse;
import shop.matddang.matddangbe.user.service.PlaceService;

import java.util.List;

@RestController
@RequestMapping("/user/v1")
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/place")
    public ResponseEntity<List<PlaceResponse>> getMyPlaces(
            @AuthenticationPrincipal User currentUser
    ) {
        String userId = currentUser.getUsername();

        List<PlaceResponse> response = placeService.getMyPlaces(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/place")
    public ResponseEntity<PlaceResponse> addPlace(
            @AuthenticationPrincipal User currentUser,
            @RequestBody PlaceUpdateRequest placeUpdateRequest
    ){
        String userId = currentUser.getUsername();

        PlaceResponse response = placeService.addPlace(placeUpdateRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PatchMapping("/place/{placeId}")
    public ResponseEntity<PlaceResponse> updatePlace(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long placeId,
            @RequestBody PlaceUpdateRequest placeUpdateRequest
    ) {
        String userId = currentUser.getUsername();

        PlaceResponse response = placeService.updatePlace(placeUpdateRequest, userId, placeId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/place/{placeId}")
    public ResponseEntity<Void> deletePlace(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long placeId
    ) {
        String userId = currentUser.getUsername();

        placeService.deletePlace(placeId, userId);
        return ResponseEntity.noContent().build();
    }


}
