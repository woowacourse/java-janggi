package domain;

public class GameContext {
    private final GameContextId id;
    private Turn turn;
    private GameState gameState;

    public GameContext(GameContextId id, Turn turn, GameState gameState) {
        this.id = id;
        this.turn = turn;
        this.gameState = gameState;
    }

    public GameContext(Turn turn, GameState gameState) {
        this(null, turn, gameState);
    }

    public GameContextId getId() {
        return id;
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
