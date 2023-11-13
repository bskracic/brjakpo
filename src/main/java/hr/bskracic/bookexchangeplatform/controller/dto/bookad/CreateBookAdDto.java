package hr.bskracic.bookexchangeplatform.controller.dto.bookad;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class CreateBookAdDto {
    private final String bookName;
    private final String description;
    private final Integer rating;
    private final Boolean active;
}
