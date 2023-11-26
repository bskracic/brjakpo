package hr.bskracic.bookexchangeplatform.controller;

import hr.bskracic.bookexchangeplatform.controller.dto.book.BookDto;
import hr.bskracic.bookexchangeplatform.controller.dto.book.CreateBookDto;
import hr.bskracic.bookexchangeplatform.controller.fixture.BookFixture;
import hr.bskracic.bookexchangeplatform.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBookAds_shouldReturnBookDtoList() {
        when(bookService.getAllBookAds()).thenReturn(List.of(BookFixture.createSampleBook()));

        List<BookDto> result = bookController.getAllBookAds();

        assertEquals(1, result.size());
        verify(bookService, times(1)).getAllBookAds();
    }

    @Test
    void getAllRecentBooks_shouldReturnBookDtoList() {
        when(bookService.getMostRecentAds()).thenReturn(List.of(BookFixture.createSampleBook()));

        List<BookDto> result = bookController.getAllRecentBooks();

        assertEquals(1, result.size());
        verify(bookService, times(1)).getMostRecentAds();
    }

    @Test
    void getBook_shouldReturnBookDto() {
        when(bookService.getBook(any(), any())).thenReturn(BookFixture.createSampleBookProjection());

        BookDto result = bookController.getBook(1L, "username");

        assertEquals("Sample Book", result.getBookName());
        verify(bookService, times(1)).getBook(1L, "username");
    }

    @Test
    void getAllBooksForUser_shouldReturnBookDtoList() {
        when(bookService.getAllBooksForUser(any())).thenReturn(List.of(BookFixture.createSampleBook()));

        List<BookDto> result = bookController.getAllBooksForUser("username");

        assertEquals(1, result.size());
        verify(bookService, times(1)).getAllBooksForUser("username");
    }

    @Test
    void createBookAd_shouldReturnBookDto() {
        CreateBookDto createBookDto = BookFixture.createSampleCreateBookDto();
        when(bookService.createBookAd(any(), any())).thenReturn(BookFixture.createSampleBook());

        BookDto result = bookController.createBookAd(createBookDto, "username");

        assertEquals("Sample Book", result.getBookName());
        verify(bookService, times(1)).createBookAd(createBookDto, "username");
    }

    @Test
    void updateBookAd_shouldReturnBookDto() {
        BookDto bookDto = BookFixture.createSampleBookDto();
        when(bookService.editBookAd(any())).thenReturn(BookFixture.createSampleBook());

        BookDto result = bookController.updateBookAd(bookDto);

        assertEquals("Sample Book", result.getBookName());
        verify(bookService, times(1)).editBookAd(bookDto);
    }

    @Test
    void deleteBookAd_shouldCallServiceMethod() {
        bookController.deleteBookAd(1L);

        verify(bookService, times(1)).deleteBookAd(1L);
    }

    @Test
    void deleteBookAd_withAdminRole_shouldCallServiceMethod() {
        doNothing().when(bookService).deleteBookAd(any());
        bookController.deleteBookAd(1L);

        verify(bookService, times(1)).deleteBookAd(1L);
    }

    @Test
    void deleteBookAd_withoutAdminRole_shouldThrowAccessDeniedException() {
        doThrow(AccessDeniedException.class).when(bookService).deleteBookAd(any());

        // Assert that AccessDeniedException is thrown
        assertThrows(AccessDeniedException.class, () -> bookController.deleteBookAd(1L));

        verify(bookService, times(1)).deleteBookAd(1L);
    }

}
