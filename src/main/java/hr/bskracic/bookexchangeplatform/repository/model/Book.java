package hr.bskracic.bookexchangeplatform.repository.model;

import hr.bskracic.bookexchangeplatform.auth.User;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="book")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="book_name", length=50)
    private String bookName;

    @Column(name="author", length=100)
    private String author;

    @Column(name="description")
    private String description;

    @Column(name="price")
    private Float price;

    @Column(name="rating")
    private Integer rating;

    @Column(name="active")
    private Boolean active;

    @Column(name="genre")
    private String genre;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Lob
    @Column(name="picture")
    private byte[] picture;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToMany(mappedBy = "book")
    private Set<BookWish> bookWishes = new HashSet<>();

    @Formula("(select count(*) from book_wish where book_wish.book_id = id)")
    private Integer wishes;
}
