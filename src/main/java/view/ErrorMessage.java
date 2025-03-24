package view;

public enum ErrorMessage {
    ERROR("[ERROR] %s");

    private final String errorMessage;

    ErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static String formatMessage(final String message) {
        return String.format(ERROR.errorMessage, message);
    }
}
