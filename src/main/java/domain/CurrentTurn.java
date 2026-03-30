package domain;

public class CurrentTurn implements TurnState {

    @Override
    public boolean isCurrent() {
        return true;
    }

    @Override
    public TurnState next() {
        return new NotCurrentTurn();
    }
}
