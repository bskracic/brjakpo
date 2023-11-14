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
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="book_ad")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookAd {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="book_name", length=50)
    private String bookName;

    @Column(name="author", length=100)
    private String author;

    @Column(name="description")
    private String description;

    @Column(name="rating")
    private Integer rating;

    @Column(name="active")
    private Boolean active;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "bookAd")
    private Set<BookAdInteraction> interactions = new HashSet<>();
}
