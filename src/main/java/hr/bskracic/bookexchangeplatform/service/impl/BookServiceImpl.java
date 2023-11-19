package hr.bskracic.bookexchangeplatform.service.impl;

import hr.bskracic.bookexchangeplatform.auth.User;
import hr.bskracic.bookexchangeplatform.controller.dto.book.BookDto;
import hr.bskracic.bookexchangeplatform.controller.dto.book.CreateBookDto;
import hr.bskracic.bookexchangeplatform.controller.dto.wish.CreateBookWishDto;
import hr.bskracic.bookexchangeplatform.repository.BookWishRepository;
import hr.bskracic.bookexchangeplatform.repository.BookRepository;
import hr.bskracic.bookexchangeplatform.repository.UserRepository;
import hr.bskracic.bookexchangeplatform.repository.model.Book;
import hr.bskracic.bookexchangeplatform.repository.model.BookWish;
import hr.bskracic.bookexchangeplatform.repository.projection.BookProjection;
import hr.bskracic.bookexchangeplatform.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookWishRepository bookWishrepository;
    private final UserRepository userRepository;

    @Override
    public List<Book> getAllBookAds() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getMostRecentAds() {
        return bookRepository.findRecentBookAds();
    }

    @Override
    public List<Book> getAllActiveAdsForUser(String username) {
        return bookRepository.findBookByUserUsernameAndActive(username, true);
    }

    @Override
    public BookProjection getBook(final Long bookId, final String username) {
        val book = bookRepository.findById(bookId).orElseThrow();
        val wished = bookRepository.isBookWishedByUser(bookId, username);
        return BookProjection.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .genre(book.getGenre())
                .price(book.getPrice())
                .active(book.getActive())
                .rating(book.getRating())
                .wishes(book.getWishes())
                .picture(book.getPicture())
                .createdAt(book.getCreatedAt())
                .wishedByUser(wished).build();
    }

    @Override
    public List<Book> getAllBooksForUser(String username) {
        return bookRepository.findBookByUserUsername(username);
    }

    @Override
    public Book createBookAd(final CreateBookDto dto, final String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(username);

        val bookAdToSave = Book.builder()
                .bookName(dto.getBookName())
                .author(dto.getAuthor())
                .description(dto.getDescription())
                .genre(dto.getGenre())
                .price(dto.getPrice())
                .picture(dto.getPicture().getBytes())
                .active(dto.getActive())
                .rating(dto.getRating())
                .user(user.get())
                .createdAt(LocalDateTime.now()).build();

        return bookRepository.save(bookAdToSave);

    }

    @Override
    public Book editBookAd(final BookDto bookDto) {
        val bookAd = this.bookRepository.findById(bookDto.getId()).orElseThrow();
        bookAd.setBookName(bookDto.getBookName());
        bookAd.setAuthor(bookDto.getAuthor());
        bookAd.setRating(bookDto.getRating());
        bookAd.setDescription(bookDto.getDescription());
        bookAd.setActive(bookDto.getActive());

        return this.bookRepository.save(bookAd);
    }

    @Override
    public void deleteBookAd(final Long id) {
        val bookAd = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(bookAd);
    }

    @Override
    public void createBookWish(final Long bookId, final String username, final String message) throws DataIntegrityViolationException {
        val bookAd = bookRepository.findById(bookId).orElseThrow();
        val user = userRepository.findUserByUsername(username).orElseThrow();
        this.bookWishrepository.save(
                BookWish.builder()
                        .book(bookAd).user(user).message(message).createdAt(LocalDateTime.now())
                        .build()
        );
    }
}
