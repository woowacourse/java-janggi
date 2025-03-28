package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import janggi.team.TeamType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Po extends Piece {

    private static final List<Integer> FORWARD_RIGHT_DIAGONAL = List.of(1, 1);
    private static final List<Integer> FORWARD_LEFT_DIAGONAL = List.of(1, -1);
    private static final List<Integer> BACKWARD_RIGHT_DIAGONAL = List.of(-1, 1);
    private static final List<Integer> BACKWARD_LEFT_DIAGONAL = List.of(-1, -1);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE_IN_GUNGSUNG = Set.of(
            FORWARD_RIGHT_DIAGONAL, FORWARD_LEFT_DIAGONAL,
            BACKWARD_RIGHT_DIAGONAL, BACKWARD_LEFT_DIAGONAL
    );

    public Po(TeamType teamType) {
        super(PieceType.PO, teamType);
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition) {
        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        if (isMovingInOnlyGungSung(currentPosition, arrivalPosition) && isDiagonalInGungSung(currentPosition,
                differenceForY, differenceForX)) {
            return new Path(
                    calculateGungSungMovingPositions(currentPosition, arrivalPosition, differenceForY, differenceForX));
        }

        validateDistanceAndDirection(differenceForY, differenceForX);
        return new Path(calculateMovingPositions(currentPosition, arrivalPosition, differenceForY, differenceForX));
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (isNotAbleToMoveDirection(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 포는 이어진 선을 따라서만 이동할 수 있습니다.");
        }
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

    private boolean isMovingInOnlyGungSung(Position currentPosition, Position arrivalPosition) {
        return Position.isInGungSung(currentPosition) && Position.isInGungSung(arrivalPosition);
    }

    private boolean isDiagonalInGungSung(Position currentPosition, int differenceForY, int differenceForX) {
        return Position.isAbleToDiagonalMoveInGungSung(currentPosition) && AVAILABLE_DIFFERENCE_IN_GUNGSUNG.contains(
                List.of(calculateUnit(differenceForY), calculateUnit(differenceForX)));
    }

    @Override
    public void validateExistPieceInPath(List<Piece> pieces, boolean hasPieceInArrivalPosition) {
        if (isConsecutiveWithOutLast(pieces, hasPieceInArrivalPosition)) {
            throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }
        if (hasPo(pieces)) {
            throw new IllegalArgumentException("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
        }
    }

    private boolean isNotAbleToMoveDirection(int differenceForY, int differenceForX) {
        return !((Math.abs(differenceForY) > 0 && Math.abs(differenceForX) == 0) ||
                (Math.abs(differenceForY) == 0 && Math.abs(differenceForX) > 0));
    }

    private boolean hasPo(List<Piece> pieces) {
        return pieces.stream()
                .anyMatch(piece -> piece.matchPieceType(getPieceType()));
    }

    private boolean isConsecutiveWithOutLast(List<Piece> pieces, boolean hasPieceInArrivalPosition) {
        if (hasPieceInArrivalPosition) {
            return pieces.size() > 2;
        }
        return pieces.size() != 1;
    }
}
