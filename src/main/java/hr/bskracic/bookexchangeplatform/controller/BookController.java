package hr.bskracic.bookexchangeplatform.controller;

import hr.bskracic.bookexchangeplatform.config.BadRequestException;
import hr.bskracic.bookexchangeplatform.config.ErrorMessage;
import hr.bskracic.bookexchangeplatform.controller.dto.book.BookDto;
import hr.bskracic.bookexchangeplatform.controller.dto.book.CreateBookDto;
import hr.bskracic.bookexchangeplatform.controller.dto.wish.CreateBookWishDto;
import hr.bskracic.bookexchangeplatform.repository.model.Book;
import hr.bskracic.bookexchangeplatform.repository.projection.BookProjection;
import hr.bskracic.bookexchangeplatform.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/all")
    @Secured("ROLE_ADMIN")
    public List<BookDto> getAllBookAds() {
        return bookService.getAllBookAds().stream().map(this::toBookDto).toList();
    }

    @GetMapping("/user")
    public List<BookDto> getAllBooksForUser(@RequestAttribute("username") final String username) {
        return bookService.getAllBooksForUser(username).stream().map(this::toBookDto).toList();
    }

    @GetMapping("/{id}")
    public BookDto getBook(
            @PathVariable("id") final Long bookId,
            @RequestAttribute("username") final String username) {
        return toBookDto(bookService.getBook(bookId, username));
    }

    @PostMapping
    public BookDto createBookAd(
            @RequestBody final CreateBookDto bookAdDto,
            @RequestAttribute("username") final String username) {

        return toBookDto(bookService.createBookAd(bookAdDto, username));
    }

    @PutMapping
    public BookDto updateBookAd(
            @RequestBody final BookDto bookDto) {
        return toBookDto(bookService.editBookAd(bookDto));
    }


    // SECURED BY ADMIN
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public void deleteBookAd(@PathVariable("id") final Long bookAdId) {
        this.bookService.deleteBookAd(bookAdId);
    }

    @PostMapping("/{id}/wish")
    public void createBookWish(
            @PathVariable("id") final Long bookAdId,
            @RequestAttribute("username") final String username,
            @RequestBody final CreateBookWishDto dto
            ) throws BadRequestException {

            try {
                this.bookService.createBookWish(bookAdId, username, dto.message());
            } catch (DataIntegrityViolationException ex) {
                throw new BadRequestException(ErrorMessage.DUPLICATE_ENTRY.getValue());
            }
    }

    private BookDto toBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .genre(book.getGenre())
                .price(book.getPrice())
                .active(book.getActive())
                .rating(book.getRating())
                .wishes(book.getWishes())
                .picture(new String(book.getPicture()))
                .createdAt(book.getCreatedAt())
                .userId(null).build();
    }

    private BookDto toBookDto(BookProjection book) {
        return BookDto.builder()
                .id(book.getId())
                .bookName(book.getBookName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .genre(book.getGenre())
                .price(book.getPrice())
                .active(book.getActive())
                .rating(book.getRating())
                .wishes(book.getWishes())
                .picture(new String(book.getPicture()))
                .createdAt(book.getCreatedAt())
                .wishedByUser(book.getWishedByUser())
                .userId(null).build();
    }

}
