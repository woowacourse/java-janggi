package domain;

public enum BoardErrorMessage {
    EMPTY_POSITION("해당 위치에는 기물이 존재하지 않습니다");

    private final String message;

    public String getMessage() {
        return message;
    }

    BoardErrorMessage(String message) {
        this.message = message;
    }
}
