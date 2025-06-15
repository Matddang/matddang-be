package shop.matddang.matddangbe.Like.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.Like.domain.Liked;

import java.util.Optional;

@Repository
public interface LikedRepository extends JpaRepository<Liked,Long> {


    Optional<Liked> findByUserIdAndsaleId(Long userId, long saleId);
}