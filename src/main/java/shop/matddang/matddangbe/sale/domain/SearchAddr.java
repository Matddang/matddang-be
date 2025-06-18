package shop.matddang.matddangbe.sale.domain;

import jakarta.persistence.*;
import lombok.*;
import shop.matddang.matddangbe.global.domain.BaseTimeEntity;

import java.time.LocalDateTime;

@Table(name = "searchAddr", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "keyword"}))
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchAddr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "keyword")
    private String keyword;

    @Column(name = "search_time")
    private LocalDateTime searchTime;

}