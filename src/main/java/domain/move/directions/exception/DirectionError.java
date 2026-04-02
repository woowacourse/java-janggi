package domain.move.directions.exception;

public enum DirectionError {

    INVALID_DIRECTION("이동할 수 없는 경로입니다."),
    ;

    private final String message;

    DirectionError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
