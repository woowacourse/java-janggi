package janggi.domain.piece;

import janggi.domain.board.BoardInfo;
import janggi.domain.path.CandidatePath;
import janggi.domain.path.Movement;
import janggi.domain.path.generator.PathStrategy;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.List;
import java.util.Objects;

public abstract class Piece {
    protected final PieceName name;
    protected final Side side;
    protected final PathStrategy pathStrategy;
    protected final Score score;

    protected Piece(PieceName name, Side side, PathStrategy pathStrategy, Score score) {
        this.name = name;
        this.side = side;
        this.pathStrategy = pathStrategy;
        this.score = score;
    }

    public final boolean isSameType(Piece piece) {
        return name == piece.name;
    }

    public final String getName() {
        return name.getNameFormat(side);
    }

    public final Score getScore() {
        return score;
    }

    public final Side getSide() {
        return side;
    }

    public final boolean isDifferentSide(Side otherSide) {
        return !this.side.equals(otherSide);
    }

    public final List<Point> availablePoints(Point from, BoardInfo boardInfo) {
        List<CandidatePath> candidatePaths = createCandidatePaths(from, boardInfo);

        return candidatePaths.stream()
                .filter(path -> isValidPath(path, boardInfo))
                .map(path -> refinePath(path, boardInfo))
                .flatMap(path -> path.getPath().stream())
                .filter(point -> boardInfo.isOtherSide(this, point))
                .toList();
    }

    protected List<CandidatePath> createCandidatePaths(Point from, BoardInfo boardInfo) {
        List<Movement> movements = getMovements();
        return movements.stream()
                .map(movement -> new CandidatePath(from, pathStrategy.calculate(movement, from, boardInfo::isInRange)))
                .toList();
    }

    protected abstract boolean isValidPath(CandidatePath candidatePath, BoardInfo boardInfo);

    protected abstract List<Movement> getMovements();

    protected abstract CandidatePath refinePath(CandidatePath candidatePath, BoardInfo boardInfo);


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
