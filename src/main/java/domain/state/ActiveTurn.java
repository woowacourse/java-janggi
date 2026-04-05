package domain.state;

public class ActiveTurn implements TurnState {
    public static final TurnState INSTANCE = new ActiveTurn();

    private ActiveTurn() {
    }

    @Override
    public boolean isCurrent() {
        return true;
    }

    @Override
    public TurnState next() {
        return InactiveTurn.INSTANCE;
    }
}
