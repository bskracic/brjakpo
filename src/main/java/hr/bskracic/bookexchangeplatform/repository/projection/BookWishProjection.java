package hr.bskracic.bookexchangeplatform.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookWishProjection {
    private Long id;
    private String username;
    private String message;
    private Boolean won;
    private LocalDateTime createdAt;
}
