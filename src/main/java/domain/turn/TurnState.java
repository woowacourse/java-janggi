package domain.turn;

public interface TurnState {
    boolean isCurrent();
    TurnState next();
}
