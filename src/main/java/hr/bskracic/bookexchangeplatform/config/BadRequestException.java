package hr.bskracic.bookexchangeplatform.config;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }
}
