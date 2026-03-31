package domain.state;

public interface TurnState {
    boolean isCurrent();
    TurnState next();
}
