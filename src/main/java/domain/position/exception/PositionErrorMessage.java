package domain.position.exception;

public enum PositionErrorMessage {
    INVALID_ROW("유효하지 않은 ROW입니다."),
    INVALID_COLUMN("유효하지 않은 COLUMN입니다.");

    private final String message;

    PositionErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
