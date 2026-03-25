package util;

public enum ErrorMessage {

    INVALID_COORDINATION("좌표값이 잘못되었습니다."),
    ;

    String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
