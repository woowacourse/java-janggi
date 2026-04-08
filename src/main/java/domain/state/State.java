package domain.state;

public interface State {

    State endGame();
    State nextTurn();
    Side getSide();
    boolean isFinished();
}
