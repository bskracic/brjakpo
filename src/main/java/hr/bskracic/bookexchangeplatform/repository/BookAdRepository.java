package hr.bskracic.bookexchangeplatform.repository;

import hr.bskracic.bookexchangeplatform.repository.model.BookAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookAdRepository extends JpaRepository<BookAd, Long> {
    List<BookAd> findBookAdByUserUsername(final String username);
}
