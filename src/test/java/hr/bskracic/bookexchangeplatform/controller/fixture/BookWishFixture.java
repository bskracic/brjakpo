package hr.bskracic.bookexchangeplatform.controller.fixture;

import hr.bskracic.bookexchangeplatform.controller.dto.wish.CreateBookWishDto;
import hr.bskracic.bookexchangeplatform.repository.projection.BookWishProjection;
import lombok.val;

import java.time.LocalDateTime;

public class BookWishFixture {
    public static BookWishProjection createSampleBookWishProjection() {
        return BookWishProjection.builder()
                .id(1L)
                .username("Sample Username")
                .won(false)
                .message("Sample Message")
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static CreateBookWishDto createSampleCreateBookWishDto() {
        return new CreateBookWishDto("Sample message");
    }
}
