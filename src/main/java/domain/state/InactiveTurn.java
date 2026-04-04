package domain.state;

public class InactiveTurn implements TurnState {
    public static final TurnState INSTANCE = new InactiveTurn();

    @Override
    public boolean isCurrent() {
        return false;
    }

    @Override
    public TurnState next() {
        return TurnState.ACTIVE;
    }
}
