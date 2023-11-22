package hr.bskracic.bookexchangeplatform.controller;

import hr.bskracic.bookexchangeplatform.config.BadRequestException;
import hr.bskracic.bookexchangeplatform.config.ErrorMessage;
import hr.bskracic.bookexchangeplatform.controller.dto.wish.CreateBookWishDto;
import hr.bskracic.bookexchangeplatform.repository.model.BookWish;
import hr.bskracic.bookexchangeplatform.repository.projection.BookWishProjection;
import hr.bskracic.bookexchangeplatform.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookWishController {

    private final BookService bookService;
    @GetMapping("/{id}/wish")
    public List<BookWishProjection> getAllWishesForBook(@PathVariable("id") final Long bookId) {
        return bookService.getAllBookWishesForBook(bookId);
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
}
