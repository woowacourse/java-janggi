package domain.state;

public class ActiveTurn implements TurnState {
    public static final TurnState INSTANCE = new ActiveTurn();

    @Override
    public boolean isCurrent() {
        return true;
    }

    @Override
    public TurnState next() {
        return TurnState.INACTIVE;
    }
}
