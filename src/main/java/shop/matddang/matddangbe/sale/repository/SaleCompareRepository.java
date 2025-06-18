package shop.matddang.matddangbe.sale.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.sale.domain.SaleCompare;


@Repository
public interface SaleCompareRepository extends JpaRepository<SaleCompare,Long>{
    @Query("SELECT COUNT(sc) > 0 FROM SaleCompare sc " +
            "WHERE (sc.saleId1 = :id1 AND sc.saleId2 = :id2) " +
            "   OR (sc.saleId1 = :id2 AND sc.saleId2 = :id1)")
    boolean existsBySaleIds(@Param("id1") Long saleId1, @Param("id2") Long saleId2);
}
