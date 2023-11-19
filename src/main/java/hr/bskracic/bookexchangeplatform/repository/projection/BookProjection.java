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
public class BookProjection {
    private Long id;
    private String bookName;
    private String author;
    private String description;
    private Float price;
    private Integer rating;
    private Boolean active;
    private String genre;
    private LocalDateTime createdAt;
    private byte[] picture;
    private Boolean wishedByUser;
    private Integer wishes;
}
