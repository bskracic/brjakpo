package hr.bskracic.bookexchangeplatform.repository.model;


import hr.bskracic.bookexchangeplatform.auth.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "book_ad_interaction",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_ad_id"})
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookAdInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_ad_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BookAd bookAd;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
