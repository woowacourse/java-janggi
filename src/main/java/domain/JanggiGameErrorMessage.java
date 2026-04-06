package domain;

public enum JanggiGameErrorMessage {
    NOW_ON_PLAYING("게임이 아직 진행 중입니다."),
    ALREADY_END("게임이 이미 종료되었습니다."),
    UNEXPECTED_SHUTDOWN("게임이 비정상인 상태로 인해 종료되었습니다");

    private final String message;

    public String getMessage() {
        return message;
    }

    JanggiGameErrorMessage(String message) {
        this.message = message;
    }
}
