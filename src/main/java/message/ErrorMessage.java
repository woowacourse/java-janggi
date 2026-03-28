package message;

public enum ErrorMessage {

    EMPTY_INPUT("[ERROR] 입력값은 공백을 입력할 수 없습니다."),
    NOT_STRICT_NUMERIC("[ERROR] 입력값은 숫자여야 합니다.(0, 음수, 공백 X)"),
    OUT_OF_RANGE_JANGGI_BOARD("[ERROR] 장기판 범위를 벗어났습니다."),
    NOT_SAME_TEAM_PIECE("[ERROR] 본인의 기물이 아닙니다."),
    ;

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
