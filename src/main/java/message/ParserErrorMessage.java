package message;

public enum ParserErrorMessage {

    INPUT_EMPTY("[ERROR] 입력값이 비어있습니다."),
    NOT_STRICT_NUMERIC("[ERROR] 입력값은 숫자여야 합니다.(0, 음수, 공백 X)"),
    INTEGER_OVERFLOW("[ERROR] 입력값이 정수의 최댓값을 초과했습니다.");

    private final String message;

    ParserErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
