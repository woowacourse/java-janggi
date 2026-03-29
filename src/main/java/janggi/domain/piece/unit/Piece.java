package janggi.domain.piece.unit;

import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Piece {
    protected PieceName name;
    protected Side side;
    protected PathStrategy pathStrategy;

    public Piece(PieceName name, Side side, PathStrategy pathStrategy) {
        this.name = name;
        this.side = side;
        this.pathStrategy = pathStrategy;
    }

    public String getName() {
        return name.getNameFormat(side);
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

    public abstract List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths);

    public abstract List<Pattern> patterns();

    protected abstract Path cutPath(Path path, Map<Point, Piece> piecesOnPaths);


    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
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

        return name == piece.name && side == piece.side;
    }

}
