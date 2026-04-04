package domain.state;

public abstract class Running implements State {

    private final Side side;

    public Running(Side side) {
        this.side = side;
    }

    @Override
    public State endGame() {
        return new Finished(side);
    }

    @Override
    public State nextTurn() {
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

    protected abstract State changeTurn();
}
