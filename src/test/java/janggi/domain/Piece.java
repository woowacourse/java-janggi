package janggi.domain;

import java.util.List;

public class Piece {

    private final Type type;
    private final Camp camp;
    private final MoveStrategy moveStrategy;

    public Piece(Type type, Camp camp, MoveStrategy moveStrategy) {
        this.type = type;
        this.camp = camp;
        this.moveStrategy = moveStrategy;
    }

    public boolean canMove(Position from, Position to) {
        List<Position> path = moveStrategy.findPath(from, to);
        return true;
    }
}
