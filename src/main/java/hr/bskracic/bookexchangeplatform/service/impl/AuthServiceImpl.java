package hr.bskracic.bookexchangeplatform.service.impl;

import hr.bskracic.bookexchangeplatform.auth.*;
import hr.bskracic.bookexchangeplatform.repository.UserRepository;
import hr.bskracic.bookexchangeplatform.service.AuthService;
import hr.bskracic.bookexchangeplatform.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User.builder().build();
        var user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .profilePicture(null)
                .language(request.getLanguage())
                .role(Role.USER)
                .build();
        userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public boolean doesUserExists(RegisterRequest request) {
        return userRepository.findUserByUsername(request.getUsername()).isPresent();
    }

    @Override
    public boolean validateFields(RegisterRequest request) {
        return request.getUsername() != null && !request.getUsername().isEmpty()
                && request.getFirstName() != null && !request.getFirstName().isEmpty()
                && request.getLastName() != null && !request.getLastName().isEmpty()
                && request.getPassword() != null && !request.getPassword().isEmpty()
                && request.getLanguage() != null && !request.getLanguage().isEmpty();
    }

    @Override
    public boolean isAdmin(String username) {
        return userRepository.findUserByUsername(username).orElseThrow().getRole().equals(Role.ADMIN);
    }
}



