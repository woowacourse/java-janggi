package janggi.domain.piece.unit;

import janggi.domain.board.Palace;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceType;
import janggi.domain.side.Side;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {
    protected PieceType type;
    protected Side side;
    protected PathStrategy pathStrategy;

    public Piece(PieceType type, Side side, PathStrategy pathStrategy) {
        this.type = type;
        this.side = side;
        this.pathStrategy = pathStrategy;
    }

    public PieceType getType() {
        return type;
    }

    public String getName() {
        return type.getNameFormat(side);
    }

    public double getScore() {
        return type.getScore();
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

    @Override
    public int hashCode() {
        int result = Objects.hashCode(type);
        result = 31 * result + Objects.hashCode(side);
        return result;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Piece piece)) {
            return false;
        }

        return type == piece.type && side == piece.side;
    }

}
