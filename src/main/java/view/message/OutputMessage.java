package view.message;

public enum OutputMessage {

    PLAYER_TURN_SIGN("%s입니다."),
    GREEN_PIECES_SCORE("초나라 기물 점수: "),
    RED_PIECES_SCORE("한나라 기물 점수: ")
    ;

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
