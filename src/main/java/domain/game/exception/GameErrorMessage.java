package domain.game.exception;

public enum GameErrorMessage {
    CHO_TURN("현재는 초의 공격 차례 입니다."),
    HAN_TURN("현재는 한의 공격 차례 입니다.");

    private final String message;

    GameErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
