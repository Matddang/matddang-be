package shop.matddang.matddangbe.crop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.crop.domain.Crop;

import java.util.List;

@Repository

public interface CropRepository extends JpaRepository<Crop,Long> {

    List<Crop> findAllByCropIdIn(List<Long> cropIds);

}
