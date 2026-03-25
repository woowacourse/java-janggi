package util;

public enum ErrorMessage {

    INVALID_COORDINATION("좌표값이 잘못되었습니다."),
    INVALID_PLACEMENT_OPTION("잘못된 입력 값입니다. 1~4 값을 입력해주세요."),
    INVALID_PIECE("알 수 없는 기물입니다: "),
    ;

    String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
