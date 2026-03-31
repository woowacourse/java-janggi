package domain.piece.error;

public enum ErrorMessage {

    IMPOSSIBLE_MOVE("기물이 움직일 수 없는 위치입니다."),
    ;

    String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
