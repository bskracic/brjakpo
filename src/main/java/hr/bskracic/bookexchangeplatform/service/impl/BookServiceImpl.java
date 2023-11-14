package hr.bskracic.bookexchangeplatform.service.impl;

import hr.bskracic.bookexchangeplatform.auth.User;
import hr.bskracic.bookexchangeplatform.controller.dto.bookad.BookAdDto;
import hr.bskracic.bookexchangeplatform.controller.dto.bookad.CreateBookAdDto;
import hr.bskracic.bookexchangeplatform.controller.dto.bookad.CreateBookAdInteractionDto;
import hr.bskracic.bookexchangeplatform.repository.BookAdInteractionRepository;
import hr.bskracic.bookexchangeplatform.repository.BookAdRepository;
import hr.bskracic.bookexchangeplatform.repository.UserRepository;
import hr.bskracic.bookexchangeplatform.repository.model.BookAd;
import hr.bskracic.bookexchangeplatform.repository.model.BookAdInteraction;
import hr.bskracic.bookexchangeplatform.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookAdRepository bookAdRepository;
    private final BookAdInteractionRepository bookAdInteractionRepo;
    private final UserRepository userRepository;

    @Override
    public List<BookAd> getAllBookAds() {
        return bookAdRepository.findAll();
    }

    @Override
    public List<BookAd> getMostRecentAds() {
        return bookAdRepository.findRecentBookAds();
    }

    @Override
    public List<BookAd> getAllActiveAdsForUser(String username) {
        return bookAdRepository.findBookAdByUserUsernameAndActive(username, true);
    }

    @Override
    public List<BookAd> getAllBooksForUser(String username) {
        return bookAdRepository.findBookAdByUserUsername(username);
    }

    @Override
    public BookAd createBookAd(final CreateBookAdDto dto, final String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if(user.isEmpty())
            throw new UsernameNotFoundException(username);

        val bookAdToSave = BookAd.builder()
                .bookName(dto.getBookName())
                .author(dto.getAuthor())
                .description(dto.getDescription())
                .active(dto.getActive())
                .rating(dto.getRating())
                .user(user.get())
                .createdAt(LocalDateTime.now()).build();

        return bookAdRepository.save(bookAdToSave);

    }

    @Override
    public BookAd editBookAd(final BookAdDto bookAdDto) {
        val bookAd = this.bookAdRepository.findById(bookAdDto.getId()).orElseThrow();
        bookAd.setBookName(bookAdDto.getBookName());
        bookAd.setAuthor(bookAdDto.getAuthor());
        bookAd.setRating(bookAdDto.getRating());
        bookAd.setDescription(bookAdDto.getDescription());
        bookAd.setActive(bookAdDto.getActive());

        return this.bookAdRepository.save(bookAd);
    }

    @Override
    public void deleteBookAd(final Long id) {
        val bookAd = bookAdRepository.findById(id).orElseThrow();
        bookAdRepository.delete(bookAd);
    }

    @Override
    public BookAdInteraction createBookInteraction(final CreateBookAdInteractionDto bookAdInteractionDto, final String username) {
        val bookAd = bookAdRepository.findById(bookAdInteractionDto.getBookAdId()).orElseThrow();
        val user = userRepository.findUserByUsername(username).orElseThrow();
        return this.bookAdInteractionRepo.save(
                BookAdInteraction.builder().bookAd(bookAd).user(user).createdAt(LocalDateTime.now()).build()
        );
    }
}
