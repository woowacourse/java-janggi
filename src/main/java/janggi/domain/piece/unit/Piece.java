package janggi.domain.piece.unit;

import janggi.domain.board.point.Point;
import janggi.domain.piece.Movement;
import janggi.domain.piece.PieceName;
import janggi.domain.piece.path.CandidatePath;
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

    public final boolean isNotEqualSide(Side otherSide) {
        return !this.side.equals(otherSide);
    }

    public final PathStrategy pathStrategy() {
        return pathStrategy;
    }

    public final List<Point> availablePoints(List<CandidatePath> candidatePaths, Map<Point, Piece> piecesOnPaths) {
        return candidatePaths.stream()
                .filter(path -> isValidPath(path, piecesOnPaths))
                .map(path -> refinePath(path, piecesOnPaths))
                .flatMap(path -> path.getPath().stream())
                .toList();
    }

    protected boolean isValidPath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths) {
        return !candidatePath.isEmpty();
    }

    public final List<CandidatePath> createCandidatePaths(Point from) {
        return convertToPaths(createCandidateMovement(), from, pathStrategy);
    }

    private List<CandidatePath> convertToPaths(List<Movement> movements, Point from, PathStrategy pathStrategy) {
        return movements.stream()
                .map(pattern -> new CandidatePath(pattern, from, pathStrategy))
                .toList();
    }

    protected abstract List<Movement> createCandidateMovement();

    protected abstract CandidatePath refinePath(CandidatePath candidatePath, Map<Point, Piece> piecesOnPaths);


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
