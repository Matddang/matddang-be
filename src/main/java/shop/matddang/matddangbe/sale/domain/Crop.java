package shop.matddang.matddangbe.sale.domain;


import jakarta.persistence.*;
import lombok.*;

@Table(name = "Crop")
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
        private Integer productionIncome;

        @Column(name = "production_cost")
        private Integer productionCost;

        @Column(name = "profit")
        private Double profit;


}
