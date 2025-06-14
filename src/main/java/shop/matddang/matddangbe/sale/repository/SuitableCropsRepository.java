package shop.matddang.matddangbe.sale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.suitableCrops.domain.SuitableCrops;

import java.util.List;
import java.util.Set;

@Repository
public interface SuitableCropsRepository extends JpaRepository<SuitableCrops, Long> {
    List<SuitableCrops> findBySaleIdInAndCropIdIn(List<Long> saleIds, List<Long> cropIds);

    List<SuitableCrops> findBySaleIdIn(List<Long> saleIdList);

    Set<SuitableCrops> findAllBySaleId(Long saleId);

}
