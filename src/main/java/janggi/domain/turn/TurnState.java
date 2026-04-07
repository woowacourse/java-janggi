package janggi.domain.turn;

public interface TurnState {

    boolean isCurrent();
    TurnState next();
}
