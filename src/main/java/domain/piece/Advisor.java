package domain.piece;

import domain.board.Board;
import domain.board.Point;
import domain.side.Side;
import java.util.List;

public class Advisor extends Piece {
    private static final PieceName NAME = PieceName.ADVISOR;

    public Advisor(Side side) {
        super(NAME.getName(side), side);
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
