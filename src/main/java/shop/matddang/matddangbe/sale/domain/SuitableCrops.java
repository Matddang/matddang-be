package shop.matddang.matddangbe.sale.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SuitableCrops")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuitableCrops {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sale_id")
    private Long saleId;

    @Column(name="crop_id")
    private Long cropId;

}