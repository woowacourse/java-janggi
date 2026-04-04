package domain.state;

public interface TurnState {
    TurnState ACTIVE = ActiveTurn.INSTANCE;
    TurnState INACTIVE = InactiveTurn.INSTANCE;

    boolean isCurrent();
    TurnState next();
}
