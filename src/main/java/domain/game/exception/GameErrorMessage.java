package domain.game.exception;

public enum GameErrorMessage {
    CHO_TURN("현재는 초의 공격 차례 입니다."),
    HAN_TURN("현재는 한의 공격 차례 입니다."),
    GAME_ALREADY_ENDED("게임이 종료되었습니다."),
    RUNNING_GAME_CANNOT_HAVE_WINNER("진행 중인 게임은 승자가 존재할 수 없습니다."),
    ENDED_GAME_MUST_HAVE_WINNER("종료된 게임은 승자가 존재해야 합니다."),
    GAME_SCORE_CANNOT_BE_TIED("장기 규칙상 덤(1.5)으로 인해 동점이 발생할 수 없습니다."),
    GAME_ID_ALREADY_EXISTS("이미 장기 게임 식별자가 존재합니다."),
    GAME_ID_REQUIRED("장기 게임 식별자가 필요합니다.");

    private final String message;

    GameErrorMessage(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
