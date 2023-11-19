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
    List<Book> findBookByUserUsername(final String username);

    @Transactional
    List<Book> findBookByUserUsernameAndActive(final String username, final boolean active);

    @Query(value =
            "select * from book b where b.active is true order by b.created_at desc",
            nativeQuery = true)
    List<Book> findRecentBookAds();

    @Query(value = "select case when count(*) > 0 then true else false end from book_wish inner join _user on _user.id = book_wish.user_id where book_id = :bookId and _user.username = :username",
            nativeQuery = true)
    Boolean isBookWishedByUser(final Long bookId, final String username);

}
