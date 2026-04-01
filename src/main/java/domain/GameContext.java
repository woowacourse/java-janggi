package domain;

public class GameContext {
    private Turn turn;
    private GameState gameState;

    public GameContext(Turn turn, GameState gameState) {
        this.turn = turn;
        this.gameState = gameState;
    }

    public Turn getTurn() {
        return turn;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void passTurn() {
        this.turn = this.turn.passTurn();
    }

    public void finishGame() {
        this.gameState = GameState.END;
    }
}
