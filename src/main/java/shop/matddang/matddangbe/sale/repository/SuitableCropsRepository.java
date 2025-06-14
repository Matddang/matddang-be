package shop.matddang.matddangbe.sale.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.sale.domain.Sale;
import shop.matddang.matddangbe.sale.domain.SuitableCrops;

import java.util.List;

@Repository
public interface SuitableCropsRepository extends JpaRepository<SuitableCrops, Long> {
    List<SuitableCrops> findBySaleIdInAndCropIdIn(List<Long> saleIds, List<Long> cropIds);
}
