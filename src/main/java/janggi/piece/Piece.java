package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Piece {

    private final PieceType pieceType;
    private final Team team;

    public Piece(PieceType pieceType, Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public Path makePath(final Position currentPosition, final Position arrivalPosition,
                         final Map<Position, Piece> pieces) {

        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        validateMove(differenceForY, differenceForX);

        final List<Position> positions = new ArrayList<>();
        int currentY = currentPosition.getY();
        int currentX = currentPosition.getX();
        currentY = moveY(arrivalPosition, differenceForY, differenceForX, currentY, positions, currentX);
        moveX(arrivalPosition, differenceForY, differenceForX, currentX, positions, currentY);

        Path path = new Path(positions);
        validatePath(pieces, path);
        return path;
    }

    protected int calculateUnit(int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

    protected boolean hasPieceInMiddle(final Path path, final Map<Position, Piece> pieces) {
        List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(pieces::containsKey);
    }

    protected abstract void validateMove(int differenceForY, int differenceForX);

    protected abstract int moveY(Position arrivalPosition, int differenceForY, final int differenceForX, int currentY,
                                 List<Position> positions, int currentX);

    protected abstract int moveX(Position arrivalPosition, final int differenceForY, int differenceForX, int currentX,
                                 List<Position> positions, int currentY);

    protected abstract void validatePath(final Map<Position, Piece> pieces, final Path path);

    public boolean matchPieceType(final PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean isSameTeam(final Team givenTeam) {
        return team.equals(givenTeam);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
