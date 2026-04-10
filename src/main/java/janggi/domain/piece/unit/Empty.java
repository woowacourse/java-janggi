package janggi.domain.piece.unit;

import java.util.List;
import java.util.Map;

import janggi.domain.board.Palace;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;

public class Empty extends Piece {

    public static final Piece INSTANCE = new Empty();

    private Empty() {
        super(Side.NONE, null);
    }

    @Override
    public PieceType getType() {
        return PieceType.NONE;
    }

    @Override
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths, Palace palace) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    public List<Pattern> patterns(Point from, Palace palace) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths, Palace palace) {
        throw new IllegalStateException("Piece Empty 객체입니다.");
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return false;
    }
}
