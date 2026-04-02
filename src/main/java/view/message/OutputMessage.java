package view.message;

public enum OutputMessage {

    PLAYER_TURN_SIGN("%s입니다."),
    ;

    private final String message;

    OutputMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
