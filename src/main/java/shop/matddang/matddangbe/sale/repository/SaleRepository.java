package shop.matddang.matddangbe.sale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.sale.domain.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
}
