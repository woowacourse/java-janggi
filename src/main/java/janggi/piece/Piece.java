package janggi.piece;

import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private final Team team;
    private Position currentPosition;

    public Piece(final Team team, final Position currentPosition) {
        this.team = team;
        this.currentPosition = currentPosition;
    }

    public final void checkMovement(final Position arrivalPosition, final Team currentTeam,
                                    final Pieces pieces) {
        validateSamePosition(arrivalPosition);
        validateOwnPiece(currentTeam);

        final int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        final int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        final PieceType pieceType = getPieceType();
        final Movement movement = findMovement(pieceType, differenceForY, differenceForX);
        final Path path = Path.from(pieceType, movement, currentPosition, arrivalPosition);
        validatePath(pieces, path);
    }

    public boolean isSamePosition(final Position givenPosition) {
        return currentPosition.equals(givenPosition);
    }

    public final boolean isSameTeam(final Team givenTeam) {
        return team.equals(givenTeam);
    }

    public void updatePosition(final Position arrivalPosition) {
        currentPosition = arrivalPosition;
    }

    protected final void validateSamePosition(final Position arrivalPosition) {
        if (currentPosition.equals(arrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
    }

    protected final int calculateUnit(final int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

    protected final boolean hasPieceInMiddle(final Path path, final Pieces pieces) {
        final List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(pieces::hasPiece);
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

    protected abstract void validatePath(Pieces pieces, Path path);

    protected abstract List<Movement> getMovements();

    private void validateOwnPiece(final Team currentTeam) {
        if (!isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("[ERROR] 자신의 팀 기물만 움직일 수 있습니다.");
        }
    }

    public abstract PieceType getPieceType();

    public final boolean matchPieceType(final PieceType givenPieceType) {
        return getPieceType() == givenPieceType;
    }

    public final Team getTeam() {
        return team;
    }
}
