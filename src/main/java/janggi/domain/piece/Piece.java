package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.coodinate.Path;
import janggi.domain.coodinate.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    protected PieceName name;
    protected Side side;

    public Piece(PieceName name, Side side) {
        this.name = name;
        this.side = side;
    }

    public final Side getSide() {
        return side;
    }
    public final boolean isSameSide(Side side) {
        return Side.isSameSide(this.side,side);
    }

    public abstract List<Point> availablePoints(Point from, Point to, Board board);

    protected abstract List<Path> path(Point from);

    protected abstract List<Path> filterPath(Path path, Board board);

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Piece piece)) {
            return false;
        }

        return name == piece.name && side == piece.side;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(side);
        return result;
    }

}
