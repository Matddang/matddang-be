package shop.matddang.matddangbe.crop.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "crop")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Crop {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "crop_id")
        private Long cropId;

        @Column(name = "crop_category")
        private String cropCategory;

        @Column(name = "crop_name")
        private String cropName;

        @Column(name = "harvest_amount")
        private Double harvestAmount;

        @Column(name = "production_income")
        private Double productionIncome;

        @Column(name = "production_cost")
        private Double productionCost;

        @Column(name = "profit")
        private Double profit;


}
