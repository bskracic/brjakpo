package hr.bskracic.bookexchangeplatform.service;

import hr.bskracic.bookexchangeplatform.auth.AuthResponse;
import hr.bskracic.bookexchangeplatform.auth.LoginRequest;
import hr.bskracic.bookexchangeplatform.auth.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    boolean doesUserExists(RegisterRequest request);

    boolean validateFields(RegisterRequest request);
}
