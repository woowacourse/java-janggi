package domain.game.exception;

public enum GameErrorMessage {
    CHO_TURN("현재는 초의 공격 차례 입니다."),
    HAN_TURN("현재는 한의 공격 차례 입니다."),
    GAME_ALREADY_ENDED("게임이 종료되었습니다."),
    RUNNING_GAME_CANNOT_HAVE_WINNER("진행 중인 게임은 승자가 존재할 수 없습니다."),
    ENDED_GAME_MUST_HAVE_WINNER("종료된 게임은 승자가 존재해야 합니다.");

    private final String message;

    GameErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
