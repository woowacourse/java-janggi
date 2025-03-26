package janggi.domain.piece;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Point;
import janggi.domain.piece.moveStrategy.MoveStrategy;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class PieceAbstractInterface implements Piece {

    protected final Set<List<Direction>> paths;
    protected final MoveStrategy moveStrategy;

    public PieceAbstractInterface(Set<List<Direction>> paths, MoveStrategy moveStrategy) {
        this.paths = paths;
        this.moveStrategy = moveStrategy;
    }

    @Override
    public final boolean isMovable(JanggiBoard janggiBoard, Point start, Point end) {
        return moveStrategy.isMovable(janggiBoard, start, end, paths);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PieceAbstractInterface that)) {
            return false;
        }
        return getClass() == o.getClass() && Objects.equals(paths, that.paths) && Objects.equals(moveStrategy,
                that.moveStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }
}
