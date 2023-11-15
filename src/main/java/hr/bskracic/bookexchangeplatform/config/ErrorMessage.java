package hr.bskracic.bookexchangeplatform.config;

public enum ErrorMessage {
    DUPLICATE_ENTRY ("error.duplicate.entry");

    private final String value;

    ErrorMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
