package hr.bskracic.bookexchangeplatform.repository;

import hr.bskracic.bookexchangeplatform.repository.model.BookAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookAdRepository extends JpaRepository<BookAd, Long> {

    List<BookAd> findBookAdByUserUsername(final String username);

    List<BookAd> findBookAdByUserUsernameAndActive(final String username, final boolean active);

    @Query(value =
        "select * from book_ad ba where ba.active is true order by ba.created_at desc",
            nativeQuery = true)
    List<BookAd> findRecentBookAds();
}
