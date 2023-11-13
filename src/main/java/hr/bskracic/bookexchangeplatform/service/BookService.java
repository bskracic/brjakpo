package hr.bskracic.bookexchangeplatform.service;

import hr.bskracic.bookexchangeplatform.controller.dto.bookad.CreateBookAdDto;
import hr.bskracic.bookexchangeplatform.repository.model.BookAd;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<BookAd> getAllBookAds();

    List<BookAd> getAllBooksForUser(String username);
    BookAd createBookAd(final CreateBookAdDto dto, final String username);
}
