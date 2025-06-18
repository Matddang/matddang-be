package shop.matddang.matddangbe.sale.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.matddang.matddangbe.sale.domain.Sale;


import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {


        @Query("SELECT s FROM Sale s " +
                "WHERE ( s.saleCategory IN (:saleCategoryList)) " +
                "AND ((s.price >= :minPrice ) AND ( s.price <= :maxPrice)) " +
                "AND ((s.area >= :minArea ) AND (s.area <= :maxArea))" +
                "AND ((s.landCategory IN (:landCategoryList)))")
        List<Sale> searchBySaleFilter(
                @Param("saleCategoryList") List<String> saleCategoryList,
                @Param("minPrice") BigDecimal minPrice,
                @Param("maxPrice") BigDecimal maxPrice,
                @Param("minArea") BigDecimal minArea,
                @Param("maxArea") BigDecimal maxArea,
                @Param("landCategoryList") List<String>landCategoryList
        );

    List<Sale> findBySaleId(Long saleId);


    @Query(value = "SELECT * FROM sale s WHERE s.sale_addr LIKE :keyword", nativeQuery = true)
    List<Sale> findBySaleAddrLike(@Param("keyword") String keyword);
}
