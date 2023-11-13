package hr.bskracic.bookexchangeplatform.controller;

import hr.bskracic.bookexchangeplatform.auth.AuthResponse;
import hr.bskracic.bookexchangeplatform.auth.LoginRequest;
import hr.bskracic.bookexchangeplatform.auth.RegisterRequest;
import hr.bskracic.bookexchangeplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        if (authService.doesUserExists(request)) {
            return ResponseEntity.badRequest().body("User Already exists");
        }
        if (!authService.validateFields(request)) {
            return ResponseEntity.badRequest().body("Missing one of the fields");
        }

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> register(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
