package util;

public enum ErrorMessage {

    INVALID_COORDINATION("좌표값이 잘못되었습니다."),
    INVALID_PLACEMENT_OPTION("잘못된 입력 값입니다. 1~4 값을 입력해주세요."),
    INVALID_PIECE("알 수 없는 기물입니다: "),
    IMPOSSIBLE_MOVE("기물이 움직일 수 없는 위치입니다."),
    NOT_EXISTS_PIECE("기물이 존재하지 않습니다."),
    NOT_SAME_TEAM("본인의 진영의 기물이 아닙니다."),
    ERROR_BLANK_INPUT("입력값이 비어 있습니다. '열,행' 형식으로 입력하세요. (예: 5,1)"),
    ERROR_TOKEN_COUNT("입력값은 쉼표(,)로 구분된 2개여야 합니다. (입력된 값 %d개) (예: 5,1)"),
    ERROR_BLANK_TOKEN("%d번째 값이 비어 있습니다. '열,행' 형식으로 입력하세요. (예: 5,1)"),
    ERROR_NUMBER_FORMAT("'%s'는 숫자가 아닙니다. 숫자만 입력하세요. (예: 5,1)"),

    ;

    String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
