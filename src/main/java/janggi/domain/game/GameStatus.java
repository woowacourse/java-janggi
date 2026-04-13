package janggi.domain.game;

public enum GameStatus {

    IN_PROGRESS("IN_PROGRESS"),
    CHU_WIN("CHU_WIN"),
    HAN_WIN("HAN_WIN"),
    ;

    private final String format;

    GameStatus(String format) {
        this.format = format;
    }

    public static GameStatus from(String gameStatusFormat) {
        for (GameStatus gameStatus : values()) {
            if (gameStatus.format.equals(gameStatusFormat)) {
                return gameStatus;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 게임 상태입니다.");
    }

    public String getFormat() {
        return format;
    }

    public boolean isInProgress() {
        return this.equals(IN_PROGRESS);
    }
}
