package domain;

public enum GameStatus {
    GREEN_PLAYER_TURN("초나라 차례"),
    RED_PLAYER_TURN("한나라 차례"),

    GREEN_TEAM_WIN("초나라 승"),
    RED_TEAM_WIN("한나라 승"),
    ;

    private final String description;

    GameStatus(String description) {
        this.description = description;
    }

    public GameStatus changePlayerTurn() {
        if (this == GameStatus.GREEN_PLAYER_TURN) {
            return RED_PLAYER_TURN;
        }
        return GREEN_PLAYER_TURN;
    }

    public boolean isFinished() {
        return this != GREEN_PLAYER_TURN && this != RED_PLAYER_TURN;
    }

    public String description() {
        return this.description;
    }
}
