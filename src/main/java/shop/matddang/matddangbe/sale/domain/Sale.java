package shop.matddang.matddangbe.sale.domain;

import jakarta.persistence.*;
import lombok.*;
import shop.matddang.matddangbe.global.domain.BaseTimeEntity;

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
    private Long sale_id;

    @Column(name = "sale_category")
    private String sale_category;

    @Column(name = "land_type")
    private String land_type;

    @Column(name = "sale_addr")
    private String sale_addr;

    @Column(name = "wgs_x")
    private Long wgs_x;

    @Column(name = "wgs_y")
    private Long wgs_y;

    @Column(name = "bcd_code")
    private String bcd_code;

    @Column(name = "land_category")
    private String land_category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "official_price")
    private BigDecimal official_price;

    @Column(name = "reg_date")
    private LocalDateTime reg_date;

    @Column(name = "area")
    private BigDecimal area;

}
