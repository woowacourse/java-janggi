package domain.piece;

import domain.board.Board;
import domain.board.Point;
import domain.side.Side;
import java.util.List;

public abstract class Piece {
    protected String name;
    protected Side side;

    public Piece(String name, Side side) {
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
