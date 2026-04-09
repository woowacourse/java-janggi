package domain.point.exception;


public enum PointError {

    POINT_RANGE_IS_OVER("좌표의 범위는 y: 0-9, x: 0-8 입니다."),
    ;

    private final String message;

    PointError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

