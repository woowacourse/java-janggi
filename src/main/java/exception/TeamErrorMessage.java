package exception;

public enum TeamErrorMessage {
    DEFAULT_TEAM_ERROR("올바르지 않은 팀 정보입니다."),
    INVALID_TEAM_NAME("존재하지 않는 팀 이름입니다.");

    private final String message;

    TeamErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
