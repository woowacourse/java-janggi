package janggi.common;

public enum ErrorMessage {
    INVALID_BOARD_POSITION("유효하지 않은 장기판 좌표입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
