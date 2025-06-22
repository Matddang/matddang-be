package shop.matddang.matddangbe.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.matddang.matddangbe.user.domain.MyPlace;

import java.util.List;

public interface PlaceRepository extends JpaRepository<MyPlace, Long> {

    List<MyPlace> findAllByUser_Id(Long userId);

}
