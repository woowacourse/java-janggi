package domain;

public interface TurnState {
    boolean isCurrent();
    TurnState next();
}
