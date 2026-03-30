package domain;

public class NotCurrentTurn implements TurnState {

    @Override
    public boolean isCurrent() {
        return false;
    }

    @Override
    public TurnState next() {
        return new CurrentTurn();
    }
}
