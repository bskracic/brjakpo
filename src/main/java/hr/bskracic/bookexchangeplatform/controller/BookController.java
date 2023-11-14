package hr.bskracic.bookexchangeplatform.controller;

import hr.bskracic.bookexchangeplatform.controller.dto.bookad.BookAdDto;
import hr.bskracic.bookexchangeplatform.controller.dto.bookad.CreateBookAdDto;
import hr.bskracic.bookexchangeplatform.controller.dto.bookad.CreateBookAdInteractionDto;
import hr.bskracic.bookexchangeplatform.repository.model.BookAd;
import hr.bskracic.bookexchangeplatform.service.BookService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/ad/all")
    public List<BookAdDto> getAllBookAds() {
        return bookService.getAllBookAds().stream().map(this::toBookAdDto).toList();
    }

    @GetMapping("/ad")
    public List<BookAdDto> getAllBooksForUser(@RequestAttribute("username") final String username) {
        return bookService.getAllBooksForUser(username).stream().map(this::toBookAdDto).toList();
    }

    @PostMapping("/ad")
    public BookAdDto createBookAd(
            @RequestBody final CreateBookAdDto bookAdDto,
            @RequestAttribute("username") final String username) {

        return toBookAdDto(bookService.createBookAd(bookAdDto, username));
    }

    @PutMapping("/ad")
    public BookAdDto updateBookAd(
            @RequestBody final BookAdDto bookAdDto
    ){
        return toBookAdDto(bookService.editBookAd(bookAdDto));
    }


    // SECURED BY ADMIN
    @DeleteMapping("/ad/{id}")
    @Secured("ROLE_ADMIN")
    public void deleteBookAd(@PathVariable("id") final Long bookAdId) {
        this.bookService.deleteBookAd(bookAdId);
    }

    @PostMapping("/ad/interaction")
    public void createAdInteraction(@RequestBody final CreateBookAdInteractionDto dto, final String username) {
        this.bookService.createBookInteraction(dto, username);
    }

    private BookAdDto toBookAdDto(BookAd bookAd) {
        return BookAdDto.builder()
                .id(bookAd.getId())
                .bookName(bookAd.getBookName())
                .author(bookAd.getAuthor())
                .description(bookAd.getDescription())
                .active(bookAd.getActive())
                .rating(bookAd.getRating())
                .createdAt(bookAd.getCreatedAt())
                .userId(null).build();

    }

}
