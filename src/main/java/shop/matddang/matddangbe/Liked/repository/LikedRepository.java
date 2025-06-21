package shop.matddang.matddangbe.Liked.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.Liked.domain.Liked;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedRepository extends JpaRepository<Liked,Long> {


    Optional<Liked> findByUserIdAndSaleId(Long userId, Long saleId);
    List<Liked> findByUserId(Long userId);
    List<Liked> findBySaleIdIn(List<Long> saleIds);
}