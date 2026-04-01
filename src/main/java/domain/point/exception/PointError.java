package domain.point.exception;


public enum PointError {

    POINT_RANGE_IS_OVER("좌표의 범위는 y: 0-9, x: 0-8 입니다."),
    POINT_IS_NOT_NUMERIC("좌표는 숫자여야 합니다."),
    POINT_INPUT_IS_BLANK("좌표 입력값이 비어있습니다."),
    POINT_PAIR_FORMAT_IS_WRONG("명령어는 '시작좌표 종료좌표' 형식으로 총 2개의 좌표를 입력해야합니다."),
    POINT_FORMAT_IS_WRONG("좌표는 'y,x' 형식이어야 합니다."),
    ;

    private final String message;

    PointError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}

