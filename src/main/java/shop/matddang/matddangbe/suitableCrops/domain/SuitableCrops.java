package shop.matddang.matddangbe.suitableCrops.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suitable_crops")
@IdClass(SuitableCropsId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuitableCrops {

    @Id
    @Column(name = "sale_id")
    private Long saleId;

    @Id
    @Column(name = "crop_id")
    private Long cropId;

}
