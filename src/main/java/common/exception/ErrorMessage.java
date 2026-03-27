package common.exception;

public enum ErrorMessage {
    INVALID_ROW_RANGE("행값은 %s이상 %s이하여야 합니다. 입력값: %s", true),
    INVALID_COLUMN_RANGE("열값은 %s이상 %s이하여야 합니다. 입력값: %s", true),
    INVALID_STRAIGHT_PATH("이동할 수 있는 직선 경로가 아닙니다."),
    INVALID_DIRECTION("갈 수 있는 경로가 없습니다. row 차이: %s, column 차이: %s", true),
    INVALID_NUMBER_INPUT("숫자만 입력해주세요."),
    INVALID_POSITION_INPUT("숫자 두 개를 공백으로 구분하여 입력하세요."),
    INVALID_PIECE_MOVEMENT("기물을 이동할 수 없습니다."),
    EMPTY_SOURCE_POSITION("비어있는 곳입니다."),
    DIFFERENT_TEAM("다른 팀의 기물입니다. 현재 차례: %s", true),
    INVALID_FORMATION_INPUT("1에서 4까지 숫자만 입력해주세요. 입력값: %s", true);

    private final String message;
    private final boolean isFormatted;

    private ErrorMessage(String message) {
        this.message = message;
        this.isFormatted = false;
    }

    private ErrorMessage(String message, boolean isFormatted) {
        this.message = message;
        this.isFormatted = isFormatted;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMessage(Object... arguments) {
        if (this.isFormatted) {
            return this.message.formatted(arguments);
        }
        return this.message;
    }

    public String formatted(Object... arguments) {
        return getMessage(arguments);
    }
}
