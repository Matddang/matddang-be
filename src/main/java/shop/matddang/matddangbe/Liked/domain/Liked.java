package shop.matddang.matddangbe.Liked.domain;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "liked")
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sale_id")
    private Long saleId;

    @Column(name="user_id")
    private String userId;

}

