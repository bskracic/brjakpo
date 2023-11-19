package hr.bskracic.bookexchangeplatform.repository;

import hr.bskracic.bookexchangeplatform.repository.model.BookWish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookWishRepository extends JpaRepository<BookWish, Long> {
}
