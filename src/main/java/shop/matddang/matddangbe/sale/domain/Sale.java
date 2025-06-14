package shop.matddang.matddangbe.sale.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "Sale")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sale_id")
    private Long saleId;

    @Column(name = "sale_category")
    private String saleCategory;

    @Column(name = "land_type")
    private String landType;

    @Column(name = "sale_addr")
    private String saleAddr;

    @Column(name = "wgs_x")
    private Double wgsX;

    @Column(name = "wgs_y")
    private Double wgsY;

    @Column(name = "land_category")
    private String landCategory;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "official_price")
    private BigDecimal officialPrice;

    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @Column(name = "area")
    private BigDecimal area;


}
