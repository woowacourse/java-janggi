package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.path.Path;
import janggi.domain.piece.path.PathStrategy;
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

    public final List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return paths.stream()
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> refinePath(path, piecesOnPaths))
                .flatMap(path -> path.getPath().stream())
                .toList();
    }

    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return !path.isEmpty();
    }

    public final List<Path> createCandidatePaths(Point from) {
        return convertToPaths(createCandidatePattern(), from, pathStrategy);
    }

    private List<Path> convertToPaths(List<Pattern> patterns, Point from, PathStrategy pathStrategy) {
        return patterns.stream()
                .map(pattern -> new Path(pattern, from, pathStrategy))
                .toList();
    }

    protected abstract List<Pattern> createCandidatePattern();

    protected abstract Path refinePath(Path path, Map<Point, Piece> piecesOnPaths);


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
