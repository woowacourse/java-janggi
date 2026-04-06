package domain;

public class GameContext {
    private final GameContextId id;
    private final Turn turn;
    private final GameState gameState;

    public GameContext(GameContextId id, Turn turn, GameState gameState) {
        this.id = id;
        this.turn = turn;
        this.gameState = gameState;
    }

    public GameContext(Turn turn, GameState gameState) {
        this(null, turn, gameState);
    }

    public GameContext passTurn() {
        return new GameContext(this.id, turn.passTurn(), this.gameState);
    }

    public GameContext finishGame() {
        return new GameContext(this.id, this.turn, GameState.END);
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
}
