package shop.matddang.matddangbe.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.matddang.matddangbe.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/v1")
@Slf4j
public class UserController {

    private final UserService userService;

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal User currentUser
            ) {

        String userId = currentUser.getUsername();
        log.debug("User with ID {} is requesting deletion", userId);

        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

}
