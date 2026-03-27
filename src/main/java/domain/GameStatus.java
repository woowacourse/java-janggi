package domain;

public enum GameStatus {
    GREEN_PLAYER_TURN,
    RED_PLAYER_TURN,
    ;

    public GameStatus changePlayerTurn() {
        if (this == GameStatus.GREEN_PLAYER_TURN) {
            return RED_PLAYER_TURN;
        }
        return GREEN_PLAYER_TURN;
    }
}
