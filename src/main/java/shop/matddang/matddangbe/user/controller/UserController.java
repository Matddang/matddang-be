package shop.matddang.matddangbe.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import shop.matddang.matddangbe.user.dto.response.UserDetailResponse;
import shop.matddang.matddangbe.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/v1")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDetailResponse> getMyDetails(
            @AuthenticationPrincipal User currentUser
    ) {
        String userId = currentUser.getUsername();
        log.debug("Fetching details for user with ID: {}", userId);

        UserDetailResponse userDetails = userService.getUserDetails(userId);
        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal User currentUser
            ) {

        String userId = currentUser.getUsername();
        log.debug("User with ID {} is requesting deletion", userId);

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/type")
    public ResponseEntity<UserDetailResponse> updateUserType(
            @AuthenticationPrincipal User currentUser,
            @RequestParam("complete") boolean testComplete
    ) {
        String userId = currentUser.getUsername();
        log.debug(String.valueOf(testComplete));

        UserDetailResponse updatedUserDetails = userService.updateUserType(userId, testComplete);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserDetails);
    }

}
