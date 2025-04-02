package janggi.domain.piece;

import janggi.domain.position.Path;
import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class GungSungPiece extends Piece {

    private static final List<Integer> FORWARD_RIGHT_DIAGONAL = List.of(1, 1);
    private static final List<Integer> FORWARD_LEFT_DIAGONAL = List.of(1, -1);
    private static final List<Integer> BACKWARD_RIGHT_DIAGONAL = List.of(-1, 1);
    private static final List<Integer> BACKWARD_LEFT_DIAGONAL = List.of(-1, -1);

    protected static final Set<List<Integer>> AVAILABLE_DIFFERENCE_IN_GUNGSUNG = Set.of(
            FORWARD_RIGHT_DIAGONAL, FORWARD_LEFT_DIAGONAL,
            BACKWARD_RIGHT_DIAGONAL, BACKWARD_LEFT_DIAGONAL
    );

    public GungSungPiece(PieceType pieceType, TeamType teamType) {
        super(pieceType, teamType);
    }

    public Path makePathForGungSungPiece(Position currentPosition, Position arrivalPosition) {
        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        if (Position.isMovingInOnlyGungSung(currentPosition, arrivalPosition) && isDiagonalInGungSung(currentPosition,
                differenceForY, differenceForX)) {
            return new Path(
                    calculateGungSungMovingPositions(currentPosition, arrivalPosition, differenceForY, differenceForX));
        }

        validateDistanceAndDirection(differenceForY, differenceForX);
        return new Path(calculateMovingPositions(currentPosition, arrivalPosition, differenceForY, differenceForX));
    }

    List<Position> calculateGungSungMovingPositions(Position currentPosition, Position arrivalPosition,
                                                    int differenceForY,
                                                    int differenceForX) {
        final List<Position> positions = new ArrayList<>();
        int currentY = currentPosition.getY();
        int currentX = currentPosition.getX();

        calculatePath(arrivalPosition, differenceForY, differenceForX, positions, currentY, currentX);
        return positions;
    }

    boolean isDiagonalInGungSung(Position currentPosition, int differenceForY, int differenceForX) {
        return Position.isAbleToDiagonalMoveInGungSung(currentPosition) && AVAILABLE_DIFFERENCE_IN_GUNGSUNG.contains(
                List.of(differenceForY, differenceForX));
    }

    private void calculatePath(Position arrivalPosition, int differenceForY, int differenceForX,
                               List<Position> positions, int currentY, int currentX) {
        int differenceUnitY = calculateUnit(differenceForY);
        int differenceUnitX = calculateUnit(differenceForX);
        while (currentX != arrivalPosition.getX() && currentY != arrivalPosition.getY()) {
            currentY += differenceUnitY;
            currentX += differenceUnitX;
            positions.add(Position.valueOf(currentY, currentX));
        }
    }
}
