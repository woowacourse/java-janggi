package domain.turn;

public class InactiveTurn implements TurnState {

    @Override
    public boolean isCurrent() {
        return false;
    }

    @Override
    public TurnState next() {
        return new ActiveTurn();
    }
}
