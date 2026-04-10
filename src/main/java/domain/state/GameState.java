package domain.state;

public interface GameState {

    GameState endGame();
    GameState nextTurn();
    Side getSide();
    boolean isFinished();
}
