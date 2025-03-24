package janggi.piece;

import janggi.Team.Team;
import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private final PieceType pieceType;
    private final Team team;

    public Piece(PieceType pieceType, Team team) {
        this.pieceType = pieceType;
        this.team = team;
    }

    public Path makePath(Position currentPosition, Position arrivalPosition) {
        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        validateMove(differenceForY, differenceForX);

        final List<Position> positions = new ArrayList<>();
        int currentY = currentPosition.getY();
        int currentX = currentPosition.getX();

        currentY = calculatePathY(arrivalPosition, differenceForY, differenceForX, positions, currentY, currentX);
        calculatePathX(arrivalPosition, differenceForY, differenceForX, positions, currentY, currentX);
        return new Path(positions);
    }

    abstract void validateMove(int differenceForY, int differenceForX);

    abstract int calculatePathY(final Position arrivalPosition, final int differenceForY, final int differenceForX,
                                final List<Position> positions, int currentY, int currentX);

    abstract int calculatePathX(final Position arrivalPosition, final int differenceForY, final int differenceForX,
                                final List<Position> positions, int currentY, int currentX);

    public boolean matchPieceType(final PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
