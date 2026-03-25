package janggi.domain.piece;

import janggi.domain.board.Board;
import janggi.domain.board.Point;
import janggi.domain.side.Side;
import java.util.List;

public class Horse extends Piece {
    private static final PieceName NAME = PieceName.HORSE;

    public Horse(Side side) {
        super(NAME, side);
    }

    @Override
    public List<Point> availablePoints(Point from, Point to, Board board) {
        return List.of();
    }

    @Override
    protected List<Path> path(Point from) {
        return List.of();
    }

    @Override
    protected List<Path> filterPath(Path path, Board board) {
        return List.of();
    }
}
