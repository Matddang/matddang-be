package shop.matddang.matddangbe.Liked.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.Liked.domain.Liked;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedRepository extends JpaRepository<Liked,Long> {


    Optional<Liked> findByUserIdAndSaleId(Long userId, Long saleId);
    List<Liked> findByUserId(Long userId);
    List<Liked> findBySaleIdIn(List<Long> saleIds);

    @Query("SELECT l.saleId FROM Liked l " +
            "WHERE l.userId = :userId AND l.saleId IN :saleIds")
    List<Long> findLikedSaleIdsByUserIdAndSaleIds(@Param("userId") Long userId,
                                                  @Param("saleIds") List<Long> saleIds);}