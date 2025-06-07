package shop.matddang.matddangbe.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.matddang.matddangbe.user.domain.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByNickName(String nickName);
}
