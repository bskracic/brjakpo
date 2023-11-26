package hr.bskracic.bookexchangeplatform.repository;

import hr.bskracic.bookexchangeplatform.repository.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Transactional
    List<Book> findBookByUserUsernameOrderByActiveDescCreatedAtDesc(final String username);

    @Query(value =
            """
            select b.*, (select count(*) from book_wish bw where bw.book_id = b.id) as wishes
            from book b
            where b.active is true order by b.active desc, b.created_at desc
            """,
            nativeQuery = true)
    @Transactional
    List<Book> findRecentBookAds();

    @Query(value = "select case when count(*) > 0 then true else false end from book_wish inner join _user on _user.id = book_wish.user_id where book_id = :bookId and _user.username = :username",
            nativeQuery = true)
    Boolean isBookWishedByUser(final Long bookId, final String username);

}
