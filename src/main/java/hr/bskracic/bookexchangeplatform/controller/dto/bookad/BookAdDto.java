package hr.bskracic.bookexchangeplatform.controller.dto.bookad;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
public class BookAdDto {
    private final Long id;
    private final String bookName;
    private final String description;
    private final Integer rating;
    private final Boolean active;
    private final LocalDateTime createdAt;
    private final Long userId;
}
