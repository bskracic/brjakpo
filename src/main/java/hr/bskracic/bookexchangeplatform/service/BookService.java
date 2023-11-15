package hr.bskracic.bookexchangeplatform.service;

import hr.bskracic.bookexchangeplatform.controller.dto.bookad.BookAdDto;
import hr.bskracic.bookexchangeplatform.controller.dto.bookad.CreateBookAdDto;
import hr.bskracic.bookexchangeplatform.controller.dto.bookad.CreateBookAdInteractionDto;
import hr.bskracic.bookexchangeplatform.repository.model.BookAd;
import hr.bskracic.bookexchangeplatform.repository.model.BookAdInteraction;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<BookAd> getAllBookAds();

    List<BookAd> getMostRecentAds();

    List<BookAd> getAllActiveAdsForUser(final String username);

    List<BookAd> getAllBooksForUser(final String username);
    BookAd createBookAd(final CreateBookAdDto dto, final String username);

    BookAd editBookAd(final BookAdDto bookAdDto);

    void deleteBookAd(final Long bookAdId);

    void createBookInteraction(final Long bookAdId, final String username) throws DataIntegrityViolationException;
}
