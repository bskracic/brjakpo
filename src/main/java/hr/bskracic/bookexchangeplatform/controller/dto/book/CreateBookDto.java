package hr.bskracic.bookexchangeplatform.controller.dto.book;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class CreateBookDto {
    private final String bookName;
    private final String author;
    private final String description;
    private final String genre;
    private final Integer rating;
    private final Boolean active;
    private final Float price;
    private final String picture;
}
