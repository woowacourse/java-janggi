package view.message;

public enum InputMessage {

    SELECT_PIECE_POSITION_SIGN("움직일 기물 좌표를 입력해 주세요. (예: 3,3)"),
    TARGET_POSITION_SIGN("이동할 좌표를 입력해 주세요. (예: 3,3)"),
    ;

    private final String message;

    InputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
