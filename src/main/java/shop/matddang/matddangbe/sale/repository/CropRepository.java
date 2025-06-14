package shop.matddang.matddangbe.sale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.sale.domain.Crop;

@Repository

public interface CropRepository extends JpaRepository<Crop,Long> {

}
