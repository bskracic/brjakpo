package hr.bskracic.bookexchangeplatform.service;

import hr.bskracic.bookexchangeplatform.controller.dto.book.BookDto;
import hr.bskracic.bookexchangeplatform.controller.dto.book.CreateBookDto;
import hr.bskracic.bookexchangeplatform.controller.fixture.BookFixture;
import hr.bskracic.bookexchangeplatform.repository.BookRepository;
import hr.bskracic.bookexchangeplatform.repository.UserRepository;
import hr.bskracic.bookexchangeplatform.repository.model.Book;
import hr.bskracic.bookexchangeplatform.repository.projection.BookProjection;
import hr.bskracic.bookexchangeplatform.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBookAds_shouldReturnListOfBooks() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(BookFixture.createSampleBook()));

        List<Book> result = bookService.getAllBookAds();

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getMostRecentAds_shouldReturnListOfBooks() {
        when(bookRepository.findRecentBookAds()).thenReturn(Collections.singletonList(BookFixture.createSampleBook()));

        List<Book> result = bookService.getMostRecentAds();

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findRecentBookAds();
    }

    @Test
    void getBook_shouldReturnBookProjection() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BookFixture.createSampleBook()));
        when(bookRepository.isBookWishedByUser(anyLong(), anyString())).thenReturn(true);

        BookProjection result = bookService.getBook(1L, "username");

        assertNotNull(result);
        assertTrue(result.getWishedByUser());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).isBookWishedByUser(1L, "username");
    }

    @Test
    void getAllBooksForUser_shouldReturnListOfBooks() {
        when(bookRepository.findBookByUserUsernameOrderByActiveDescCreatedAtDesc(anyString()))
                .thenReturn(Collections.singletonList(BookFixture.createSampleBook()));

        List<Book> result = bookService.getAllBooksForUser("username");

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findBookByUserUsernameOrderByActiveDescCreatedAtDesc("username");
    }

    @Test
    void createBookAd_shouldReturnBook() {
        CreateBookDto createBookDto = BookFixture.createSampleCreateBookDto();
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.of(BookFixture.createSampleUser()));
        when(bookRepository.save(any())).thenReturn(BookFixture.createSampleBook());

        Book result = bookService.createBookAd(createBookDto, "username");

        assertNotNull(result);
        assertEquals("Sample Book" , result.getBookName());
        verify(userRepository, times(1)).findUserByUsername("username");
        verify(bookRepository, times(1)).save(any());
    }

    @Test
    void createBookAd_withNonExistingUser_shouldThrowUsernameNotFoundException() {
        CreateBookDto createBookDto = BookFixture.createSampleCreateBookDto();
        when(userRepository.findUserByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> bookService.createBookAd(createBookDto, "nonexistentUser"));

        verify(userRepository, times(1)).findUserByUsername("nonexistentUser");
        verify(bookRepository, never()).save(any());
    }

    @Test
    void deleteBookAd_shouldCallRepositoryDelete() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(BookFixture.createSampleBook()));
        doNothing().when(bookRepository).delete(any());

        assertDoesNotThrow(() -> bookService.deleteBookAd(1L));

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(any());
    }

    private BookDto createEditBookDto() {
        return BookDto.builder()
                .id(1L)
                .bookName("Edited Name")
                .author("Sample Author")
                .description("Sample Description")
                .genre("Sample Genre")
                .price(29.99f)
                .active(true)
                .rating(4)
                .wishes(3)
                .picture("Sample Picture")
                .createdAt(LocalDateTime.now())
                .userId(null)
                .build();
    }
}
