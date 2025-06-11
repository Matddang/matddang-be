package shop.matddang.matddangbe.sale.domain;

import jakarta.persistence.*;
import lombok.*;
import shop.matddang.matddangbe.global.domain.BaseTimeEntity;

import java.time.LocalDateTime;

@Table(name = "Sale")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="saleId")
    private Long saleId;

    @Column(name = "saleCategory")
    private String saleCategory;

    @Column(name = "saleAddr")
    private String saleAddr;

    @Column(name = "bcdCode")
    private Long bcdCode;

    @Column(name = "landType")
    private String landType;

    @Column(name = "price")
    private Long price;

    @Column(name = "saleState")
    private String saleState;

    @Column(name = "officialPrice")
    private Long officialPrice;

    @Column(name = "regDate")
    private LocalDateTime regDate;

    @Column(name = "profit")
    private Long profit;

    @Column(name = "area")
    private Long area;

}
