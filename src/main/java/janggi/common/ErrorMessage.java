package janggi.common;

public enum ErrorMessage {
    INVALID_BOARD_POSITION("유효하지 않은 장기판 좌표입니다."),
    INVALID_POSITION_INPUT("올바른 좌표 형식이 아닙니다."),
    INVALID_NUMBER_INPUT("숫자로 입력해주세요."),
    POSITION_DOES_NOT_EXIST("해당 좌표에 기물이 존재하지 않습니다."),
    NOT_SAME_SIDE("상대의 기물입니다.")
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
