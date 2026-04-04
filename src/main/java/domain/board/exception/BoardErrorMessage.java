package domain.board.exception;

public enum BoardErrorMessage {
    INVALID_SANG_SETUP("잘못된 상차림 번호입니다.");

    private final String message;

    BoardErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
