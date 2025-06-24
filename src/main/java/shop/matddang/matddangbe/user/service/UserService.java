package shop.matddang.matddangbe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.matddang.matddangbe.user.converter.UserConverter;
import shop.matddang.matddangbe.user.domain.UserEntity;
import shop.matddang.matddangbe.user.dto.response.UserDetailResponse;
import shop.matddang.matddangbe.user.exception.AuthenticationException;
import shop.matddang.matddangbe.user.repository.UserRepository;

import static shop.matddang.matddangbe.user.exception.AuthenticationErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Transactional
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

    public UserDetailResponse getUserDetails(String userId) {

        UserEntity user = findUser(userId);

        return userConverter.toUserDetailResponse(user);
    }

    public UserEntity findUser(String userId) {
        return userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new AuthenticationException(USER_NOT_FOUND, String.format("User with ID %s not found", userId)));
    }

    @Transactional
    public UserDetailResponse updateUserType(String userId, boolean testComplete) {

        UserEntity user = findUser(userId);

        user.updateUserType(testComplete);
        return userConverter.toUserDetailResponse(user);
    }
}
