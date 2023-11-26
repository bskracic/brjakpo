package hr.bskracic.bookexchangeplatform.service.impl;

import hr.bskracic.bookexchangeplatform.repository.BookRepository;
import hr.bskracic.bookexchangeplatform.repository.BookWishRepository;
import hr.bskracic.bookexchangeplatform.repository.UserRepository;
import hr.bskracic.bookexchangeplatform.repository.model.BookWish;
import hr.bskracic.bookexchangeplatform.repository.projection.BookWishProjection;
import hr.bskracic.bookexchangeplatform.service.BookWishService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookWishServiceImpl implements BookWishService {

    private final BookWishRepository bookWishRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public List<BookWishProjection> getAllBookWishesForBook(final Long bookId) {
        return bookWishRepository.findBookWishByBookId(bookId).stream()
                .map(bw -> BookWishProjection.builder()
                        .id(bw.getId())
                        .username(bw.getUser().getUsername())
                        .message(bw.getMessage())
                        .won(bw.getWon())
                        .createdAt(bw.getCreatedAt()).build())
                .toList();
    }

    @Override
    public void createBookWish(final Long bookId, final String username, final String message) throws DataIntegrityViolationException {
        val bookAd = bookRepository.findById(bookId).orElseThrow();
        val user = userRepository.findUserByUsername(username).orElseThrow();
        bookWishRepository.save(
                BookWish.builder()
                        .book(bookAd).user(user).message(message).won(false).createdAt(LocalDateTime.now())
                        .build()
        );
    }

    @Override
    public void markBookWishAsWin(Long bookWishId) {
        val bookWish = bookWishRepository.findById(bookWishId).orElseThrow();
        val book = bookRepository.findById(bookWish.getBook().getId()).orElseThrow();
        book.setActive(false);
        bookWish.setWon(true);
        this.bookRepository.save(book);
        this.bookWishRepository.save(bookWish);
    }
}
