package domain.turn;

public class ActiveTurn implements TurnState {

    @Override
    public boolean isCurrent() {
        return true;
    }

    @Override
    public TurnState next() {
        return new InactiveTurn();
    }
}
