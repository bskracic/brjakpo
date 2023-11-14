package hr.bskracic.bookexchangeplatform.controller.dto.bookad;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class CreateBookAdInteractionDto {
    final Long userId;
    final Long bookAdId;
}
