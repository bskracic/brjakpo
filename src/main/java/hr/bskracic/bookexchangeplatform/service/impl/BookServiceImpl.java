package hr.bskracic.bookexchangeplatform.service.impl;

import hr.bskracic.bookexchangeplatform.auth.User;
import hr.bskracic.bookexchangeplatform.controller.dto.bookad.CreateBookAdDto;
import hr.bskracic.bookexchangeplatform.repository.BookAdRepository;
import hr.bskracic.bookexchangeplatform.repository.UserRepository;
import hr.bskracic.bookexchangeplatform.repository.model.BookAd;
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
    private final UserRepository userRepository;

    @Override
    public List<BookAd> getAllBookAds() {
        return bookAdRepository.findAll();
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
                .description(dto.getDescription())
                .active(dto.getActive())
                .rating(dto.getRating())
                .user(user.get())
                .createdAt(LocalDateTime.now()).build();

        return bookAdRepository.save(bookAdToSave);

    }
}
