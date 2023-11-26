package hr.bskracic.bookexchangeplatform.controller;

import hr.bskracic.bookexchangeplatform.config.BadRequestException;
import hr.bskracic.bookexchangeplatform.config.ErrorMessage;
import hr.bskracic.bookexchangeplatform.controller.dto.wish.CreateBookWishDto;
import hr.bskracic.bookexchangeplatform.controller.fixture.BookWishFixture;
import hr.bskracic.bookexchangeplatform.repository.projection.BookWishProjection;
import hr.bskracic.bookexchangeplatform.service.BookWishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BookWishControllerTest {

    @Mock
    private BookWishService bookWishService;

    @InjectMocks
    private BookWishController bookWishController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllWishesForBook_shouldReturnBookWishProjections() {
        when(bookWishService.getAllBookWishesForBook(anyLong())).thenReturn(List.of(BookWishFixture.createSampleBookWishProjection()));

        List<BookWishProjection> result = bookWishController.getAllWishesForBook(1L);

        assertEquals(1, result.size());
        verify(bookWishService, times(1)).getAllBookWishesForBook(1L);
    }

    @Test
    void createBookWish_shouldCreateBookWish() throws BadRequestException {
        CreateBookWishDto createBookWishDto = BookWishFixture.createSampleCreateBookWishDto();
        doNothing().when(bookWishService).createBookWish(anyLong(), anyString(), anyString());

        assertDoesNotThrow(() -> bookWishController.createBookWish(1L, "username", createBookWishDto));

        verify(bookWishService, times(1)).createBookWish(1L, "username", createBookWishDto.message());
    }

    @Test
    void createBookWish_withDuplicateEntry_shouldThrowBadRequestException() {
        CreateBookWishDto createBookWishDto = BookWishFixture.createSampleCreateBookWishDto();
        doThrow(DataIntegrityViolationException.class).when(bookWishService).createBookWish(anyLong(), anyString(), anyString());

        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> bookWishController.createBookWish(1L, "username", createBookWishDto));

        assertEquals(ErrorMessage.DUPLICATE_ENTRY.getValue(), exception.getMessage());

        verify(bookWishService, times(1)).createBookWish(1L, "username", createBookWishDto.message());
    }

    @Test
    void markBookWishAsWin_shouldMarkBookWishAsWin() {
        doNothing().when(bookWishService).markBookWishAsWin(anyLong());

        assertDoesNotThrow(() -> bookWishController.markBookWishAsWin(1L));

        verify(bookWishService, times(1)).markBookWishAsWin(1L);
    }
}
