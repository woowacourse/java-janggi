package domain;

import domain.strategy.MoveStrategy;

public class Piece {

    private final Team team;
    private final Type type;
    private final MoveStrategy moveStrategy;

    private Piece(final Team team, final Type type, final MoveStrategy moveStrategy) {
        this.team = team;
        this.type = type;
        this.moveStrategy = moveStrategy;
    }

    public static Piece of(final Team team, final Type type, final MoveStrategy moveStrategy) {
        return new Piece(team, type, moveStrategy);
    }

    public Type getType() {
        return type;
    }
}
