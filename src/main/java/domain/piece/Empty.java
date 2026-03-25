package domain.piece;

import domain.board.Board;
import domain.board.Point;
import java.util.List;

public class Empty extends Piece{
    public Empty() {
        super(null,null);
    }

    @Override
    public List<Point> availablePoints(Point from, Point to, Board board) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    protected List<Path> path(Point from) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    protected List<Path> filterPath(Path path, Board board) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }
}
