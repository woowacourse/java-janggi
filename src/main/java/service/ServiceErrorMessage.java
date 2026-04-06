package service;

public enum ServiceErrorMessage {
    DATABASE_LINKING_FAIL_EXCEPTION("DB 연결에 실패하였습니다."),
    ROLL_BACK("비즈니스 로직으로 인한 롤백이 발생하였습니다."),
    NOT_FOUND_PLAYING_GAME("진행 중인 게임이 없습니다.");

    private final String message;

    public String getMessage() {
        return message;
    }

    ServiceErrorMessage(String message) {
        this.message = message;
    }
}
