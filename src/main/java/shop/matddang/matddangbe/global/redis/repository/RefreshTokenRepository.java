package shop.matddang.matddangbe.global.redis.repository;

import org.springframework.data.repository.CrudRepository;
import shop.matddang.matddangbe.global.redis.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
