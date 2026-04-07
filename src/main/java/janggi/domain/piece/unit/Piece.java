package janggi.domain.piece.unit;

import java.util.List;
import java.util.Map;

import janggi.domain.board.Palace;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;

public abstract class Piece {

    private final Side side;
    private final PathStrategy pathStrategy;

    public Piece(Side side, PathStrategy pathStrategy) {
        this.side = side;
        this.pathStrategy = pathStrategy;
    }

    public abstract PieceType getType();

    public String getName() {
        return getType().getNameFormat(side);
    }

    public double getScore() {
        return getType().getScore();
    }

    public final Side getSide() {
        return side;
    }

    public final boolean isOtherSide(Side side) {
        return !Side.isSameSide(this.side, side);
    }

    public final PathStrategy pathStrategy() {
        return pathStrategy;
    }

    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return !path.isEmpty();
    }

    public abstract List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths, Palace palace);

    public abstract List<Pattern> patterns(Point from, Palace palace);

    protected abstract Path cutPath(Path path, Map<Point, Piece> piecesOnPaths, Palace palace);
}
