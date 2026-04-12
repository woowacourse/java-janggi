package domain.state;

public abstract class Running implements GameState {

    private final Side side;

    public Running(Side side) {
        this.side = side;
    }

    @Override
    public GameState endGame() {
        return new Finished(side);
    }

    @Override
    public GameState nextTurn() {
        return changeTurn();
    }

    @Override
    public Side getSide() {
        return side;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    protected abstract GameState changeTurn();
}
