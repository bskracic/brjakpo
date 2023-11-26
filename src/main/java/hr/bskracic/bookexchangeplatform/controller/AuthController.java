package hr.bskracic.bookexchangeplatform.controller;

import hr.bskracic.bookexchangeplatform.auth.AuthResponse;
import hr.bskracic.bookexchangeplatform.auth.LoginRequest;
import hr.bskracic.bookexchangeplatform.auth.RegisterRequest;
import hr.bskracic.bookexchangeplatform.config.UnauthorizedException;
import hr.bskracic.bookexchangeplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/valid")
    public ResponseEntity<String> checkValidToken() {
        return ResponseEntity.ok("valid");
    }

    @GetMapping("/is-admin")
    public ResponseEntity<String> isAdmin(@RequestAttribute("username") final String username) throws UnauthorizedException {
        if(!this.authService.isAdmin(username))
            throw new UnauthorizedException("not an admin");

        return ResponseEntity.ok("valid");
    }
}
