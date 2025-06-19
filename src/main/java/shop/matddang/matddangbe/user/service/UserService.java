package shop.matddang.matddangbe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shop.matddang.matddangbe.user.exception.AuthenticationException;
import shop.matddang.matddangbe.user.repository.UserRepository;

import static shop.matddang.matddangbe.user.exception.AuthenticationErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void deleteUser(String userId) {
        log.debug("Deleting user with ID: {}", userId);

        userRepository.findById(Long.parseLong(userId))
                .ifPresentOrElse(
                        user -> {
                            userRepository.delete(user);
                            log.info("User with ID {} has been deleted", userId);
                        },
                        () -> {throw new AuthenticationException(USER_NOT_FOUND);}
                );
    }


}
