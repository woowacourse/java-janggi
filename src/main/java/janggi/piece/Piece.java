package janggi.piece;

import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Piece {

    private final PieceType pieceType;
    private final Team team;

    public Piece(final PieceType pieceType, final Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public final Path makePath(final Position currentPosition, final Position arrivalPosition,
                         final Map<Position, Piece> pieces) {
        final int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        final int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        final Movement movement = findMovement(pieceType, differenceForY, differenceForX);
        final Path path = Path.from(pieceType, movement, currentPosition, arrivalPosition);
        validatePath(pieces, path);
        return path;
    }

    public final boolean matchPieceType(final PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public final boolean isSameTeam(final Team givenTeam) {
        return team.equals(givenTeam);
    }

    protected final int calculateUnit(final int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

    protected final boolean hasPieceInMiddle(final Path path, final Map<Position, Piece> pieces) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(pieces::containsKey);
    }

    protected final Movement findMovement(final PieceType pieceType, int dy, int dx) {
        if (pieceType.isIterable()) {
            dy = calculateUnit(dy);
            dx = calculateUnit(dx);
        }
        final int y = dy;
        final int x = dx;
        return getMovements().stream()
                .filter(movement -> movement.isSameMovement(y, x))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 적절한 움직임이 아닙니다."));
    }

    protected abstract void validatePath(Map<Position, Piece> pieces, Path path);

    protected abstract List<Movement> getMovements();

    public final PieceType getPieceType() {
        return pieceType;
    }

    public final Team getTeam() {
        return team;
    }
}
