package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import janggi.team.TeamType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Jol extends Piece {

    private static final List<Integer> FORWARD = List.of(-1, 0);
    private static final List<Integer> RIGHT = List.of(0, 1);
    private static final List<Integer> LEFT = List.of(0, -1);

    private static final List<Integer> FORWARD_RIGHT_DIAGONAL = List.of(1, 1);
    private static final List<Integer> FORWARD_LEFT_DIAGONAL = List.of(1, -1);
    private static final List<Integer> BACKWARD_RIGHT_DIAGONAL = List.of(-1, 1);
    private static final List<Integer> BACKWARD_LEFT_DIAGONAL = List.of(-1, -1);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            FORWARD, RIGHT, LEFT
    );

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE_IN_GUNGSUNG = Set.of(
            FORWARD_RIGHT_DIAGONAL, FORWARD_LEFT_DIAGONAL,
            BACKWARD_RIGHT_DIAGONAL, BACKWARD_LEFT_DIAGONAL
    );

    public Jol() {
        super(PieceType.JOL, TeamType.CHO);
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition) {
        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        if (isMovingInOnlyGungSung(currentPosition, arrivalPosition) && isDiagonaInGungSung(currentPosition,
                differenceForY, differenceForX)) {
            return new Path(
                    calculateGungSungMovingPositions(currentPosition, arrivalPosition, differenceForY, differenceForX));
        }

        validateDistanceAndDirection(differenceForY, differenceForX);
        return new Path(calculateMovingPositions(currentPosition, arrivalPosition, differenceForY, differenceForX));
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 졸은 앞 방향으로 이어진 선을 따라 한 칸 씩만 이동할 수 있습니다.");
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

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !AVAILABLE_DIFFERENCE.contains(List.of(differenceForY, differenceForX));
    }

    private boolean isMovingInOnlyGungSung(Position currentPosition, Position arrivalPosition) {
        return Position.isInGungSung(currentPosition) && Position.isInGungSung(arrivalPosition);
    }

    private boolean isDiagonaInGungSung(Position currentPosition, int differenceForY, int differenceForX) {
        return Position.isAbleToDiagonalMoveInGungSung(currentPosition) && AVAILABLE_DIFFERENCE_IN_GUNGSUNG.contains(
                List.of(differenceForY, differenceForX)) && (differenceForY < 0);
    }
}
