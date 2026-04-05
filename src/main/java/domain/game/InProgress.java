package domain.game;

public class InProgress implements GameState {

    @Override
    public void validateMovable() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
