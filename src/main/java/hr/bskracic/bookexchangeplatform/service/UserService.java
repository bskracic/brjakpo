package hr.bskracic.bookexchangeplatform.service;

import hr.bskracic.bookexchangeplatform.auth.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;


public interface UserService extends UserDetailsService {

    long getCount();
    Optional<User> findUserByUsername(String username);
    User createUser(User user);

    User updateUser(User user);
    boolean deleteUser(Long userId);

    User getUserById(long userId);

    List<User> getAllUsers();

//    User saveUserWithNewAccount(User user, Account newAccount);

}
