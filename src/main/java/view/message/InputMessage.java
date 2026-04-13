package view.message;

public enum InputMessage {

    SELECT_PIECE_POSITION_SIGN("움직일 기물 좌표를 입력해 주세요. (예: 3,3)"),
    TARGET_POSITION_SIGN("이동할 좌표를 입력해 주세요. (예: 3,3)"),
    INPUT_GAME_START_OPTION("새 게임을 시작하려면 1, 이어 하려면 2를 입력하세요.")
    ;

    private final String message;

    InputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
