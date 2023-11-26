package hr.bskracic.bookexchangeplatform.config;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(String message) {
        super(message);
    }
}
