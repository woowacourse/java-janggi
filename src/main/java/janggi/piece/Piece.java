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

        validateDistanceAndDirection(differenceForY, differenceForX);

        List<Position> positions = calculateMovingPositions(currentPosition, arrivalPosition, differenceForY,
                differenceForX);
        return new Path(positions);
    }

    public void validateExistPieceInPath(List<Piece> pieces, boolean hasPieceInArrivalPosition) {
        if ((hasPieceInArrivalPosition && pieces.size() > 1) || (!hasPieceInArrivalPosition && !pieces.isEmpty())) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    abstract void validateDistanceAndDirection(int differenceForY, int differenceForX);

    List<Position> calculateMovingPositions(Position currentPosition, Position arrivalPosition, int differenceForY,
                                            int differenceForX) {
        final List<Position> positions = new ArrayList<>();
        int currentY = currentPosition.getY();
        int currentX = currentPosition.getX();

        currentY = calculatePathY(arrivalPosition, differenceForY, differenceForX, positions, currentY, currentX);
        calculatePathX(arrivalPosition, differenceForY, differenceForX, positions, currentY, currentX);
        return positions;
    }

    int calculatePathY(Position arrivalPosition, int differenceForY, int differenceForX,
                       List<Position> positions, int currentY, int currentX) {
        int differenceUnitY = calculateUnit(differenceForY);
        while (currentY != arrivalPosition.getY()) {
            currentY += differenceUnitY;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentY;
    }

    void calculatePathX(Position arrivalPosition, int differenceForY, int differenceForX,
                        List<Position> positions, int currentY, int currentX) {
        int differenceUnitX = calculateUnit(differenceForX);
        while (currentX != arrivalPosition.getX()) {
            currentX += differenceUnitX;
            positions.add(Position.valueOf(currentY, currentX));
        }
    }

    int calculateUnit(int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

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
