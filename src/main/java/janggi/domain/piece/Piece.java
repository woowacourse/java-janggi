package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.board.Point;
import janggi.domain.side.Side;
import java.util.List;

public abstract class Piece {
    protected PieceName name;
    protected Side side;

    public Piece(PieceName name, Side side) {
        this.name = name;
        this.side = side;
    }

    public final boolean isSameSide(Side side) {
        return this.side.equals(side);
    }

    public abstract List<Point> availablePoints(Point from, Point to, Board board);

    protected abstract List<Path> path(Point from);

    protected abstract List<Path> filterPath(Path path, Board board);

    public final Side getSide() {
        return side;
    }
}
