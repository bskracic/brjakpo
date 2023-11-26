package hr.bskracic.bookexchangeplatform.service;

import hr.bskracic.bookexchangeplatform.repository.projection.BookWishProjection;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface BookWishService {
    void createBookWish(final Long bookId, final String username, final String message) throws DataIntegrityViolationException;

    List<BookWishProjection> getAllBookWishesForBook(final Long bookId);


    void markBookWishAsWin(final Long bookWishId);

}
