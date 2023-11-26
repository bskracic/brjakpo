package hr.bskracic.bookexchangeplatform.controller.fixture;

import hr.bskracic.bookexchangeplatform.auth.User;
import hr.bskracic.bookexchangeplatform.controller.dto.book.BookDto;
import hr.bskracic.bookexchangeplatform.controller.dto.book.CreateBookDto;
import hr.bskracic.bookexchangeplatform.repository.model.Book;
import hr.bskracic.bookexchangeplatform.repository.projection.BookProjection;

import java.time.LocalDateTime;

public class BookFixture {
    public static Book createSampleBook() {
        return Book.builder()
                .id(1L)
                .bookName("Sample Book")
                .author("Sample Author")
                .description("Sample Description")
                .price(29.99f)
                .rating(4)
                .active(true)
                .genre("Sample Genre")
                .createdAt(LocalDateTime.now())
                .picture("Sample Picture".getBytes())
                .user(createSampleUser())
                .build();
    }

    public static User createSampleUser() {
        return User.builder()
                .id(1L)
                .username("sampleUser")
                .password("samplePassword")
                .build();
    }

    public static BookDto createSampleBookDto() {
        return BookDto.builder()
                .id(1L)
                .bookName("Sample Book")
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

    public static CreateBookDto createSampleCreateBookDto() {
        return CreateBookDto.builder()
                .bookName("Sample Book")
                .author("Sample Author")
                .description("Sample Description")
                .genre("Sample Genre")
                .price(69.66f)
                .active(true)
                .rating(1)
                .picture("Sample Picture")
                .build();
    }

    public static BookProjection createSampleBookProjection() {
        return BookProjection.builder()
                .id(1L)
                .bookName("Sample Book")
                .author("Sample Author")
                .description("Sample Description")
                .price(29.99f)
                .rating(4)
                .active(true)
                .genre("Sample Genre")
                .createdAt(LocalDateTime.now())
                .picture("Sample Picture".getBytes())  // Convert string to byte array for simplicity
                .wishedByUser(true)
                .wishes(3)
                .currentUserOwner(true)
                .build();
    }

}
