package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return type == piece.type && camp == piece.camp && Objects.equals(moveStrategy, piece.moveStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, camp, moveStrategy);
    }
}
