package hr.bskracic.bookexchangeplatform.service;

import hr.bskracic.bookexchangeplatform.controller.dto.book.BookDto;
import hr.bskracic.bookexchangeplatform.controller.dto.book.CreateBookDto;
import hr.bskracic.bookexchangeplatform.repository.model.Book;
import hr.bskracic.bookexchangeplatform.repository.model.BookWish;
import hr.bskracic.bookexchangeplatform.repository.projection.BookProjection;
import hr.bskracic.bookexchangeplatform.repository.projection.BookWishProjection;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> getAllBookAds();

    List<Book> getMostRecentAds();

    List<Book> getAllActiveAdsForUser(final String username);

    List<BookWishProjection> getAllBookWishesForBook(final Long bookId);

    BookProjection getBook(final Long bookId, final String username);

    List<Book> getAllBooksForUser(final String username);
    Book createBookAd(final CreateBookDto dto, final String username);

    Book editBookAd(final BookDto bookDto);

    void deleteBookAd(final Long bookAdId);

    void createBookWish(final Long bookId, final String username, final String message) throws DataIntegrityViolationException;
}
