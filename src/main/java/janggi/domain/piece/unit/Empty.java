package janggi.domain.piece.unit;

import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Directions;
import janggi.domain.piece.PieceName;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;

public class Empty extends Piece {
    private static final PieceName NAME = PieceName.NONE;

    public Empty() {
        super(NAME, Side.NONE, null);
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    public List<Directions> directions() {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return false;
    }
}
