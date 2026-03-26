package janggi.domain.piece;

import janggi.domain.coordinate.Path;
import janggi.domain.coordinate.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;

public class Empty extends Piece {
    private static final PieceName NAME = PieceName.NONE;

    public Empty() {
        super(NAME, Side.NONE);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    public List<Path> path(Point from) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }
}
