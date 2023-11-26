package hr.bskracic.bookexchangeplatform.controller.dto.book;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
public class BookDto {
    private final Long id;
    private final String bookName;
    private final String author;
    private final Float price;
    private final String genre;
    private final String description;
    private final Integer rating;
    private final Boolean active;
    private final LocalDateTime createdAt;
    private final Long userId;
    private final String picture;
    private final Boolean wishedByUser;
    private final Integer wishes;
    private final Boolean currentUserOwner;
}
