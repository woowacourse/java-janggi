package domain.game;

public interface GameState {
    void validateMovable();

    boolean isFinished();
}
