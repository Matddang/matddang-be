package shop.matddang.matddangbe.sale.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Table(name = "saleCompare")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleCompare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "sale_id1")
    private Long saleId1;

    @Column(name = "sale_id2")
    private Long saleId2;


    @Column(name = "search_time")
    private LocalDateTime searchTime;


}
