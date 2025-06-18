package shop.matddang.matddangbe.sale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.sale.domain.SearchAddr;

import java.util.List;

@Repository
public interface SearchAddrRepository extends JpaRepository<SearchAddr,Long> {
    List<SearchAddr> findByUserIdOrderBySearchTimeAsc(Long UserId);
    List<SearchAddr> findByUserIdAndKeyword(Long userId, String keyword);
    List<SearchAddr> findByUserIdOrderBySearchTimeDesc(Long userId);
}
