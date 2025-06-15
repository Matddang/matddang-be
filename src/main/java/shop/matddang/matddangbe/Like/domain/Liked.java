package shop.matddang.matddangbe.Like.domain;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "Like")
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
    private Long userId;

}

