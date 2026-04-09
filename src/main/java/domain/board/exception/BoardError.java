package domain.board.exception;

public enum BoardError {

    FORMATION_IS_NOT_NUMERIC("포메이션 번호는 숫자로 입력해주세요."),
    FORMATION_NUMBER_RANGE_IS_INVALID("포메이션 번호는 1-4사이의 숫자입니다."),

    BOARD_NOT_FOUND("해당 Board를 찾을 수 없습니다."),
    ;

    private final String message;

    BoardError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
