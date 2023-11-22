package hr.bskracic.bookexchangeplatform.repository;

import hr.bskracic.bookexchangeplatform.repository.model.BookWish;
import hr.bskracic.bookexchangeplatform.repository.projection.BookWishProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookWishRepository extends JpaRepository<BookWish, Long> {
    @Query(value = "select * from book_wish bw where bw.book_id = :bookId", nativeQuery = true)
    @Transactional
    List<BookWish> findBookWishByBookId(final Long bookId);
}
