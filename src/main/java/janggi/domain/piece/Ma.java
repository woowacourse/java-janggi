package janggi.domain.piece;

import janggi.domain.position.Position;
import janggi.domain.team.TeamType;
import java.util.List;
import java.util.Set;

public class Ma extends Piece {

    private static final List<Integer> FORWARD_RIGHT_DIAGONAL = List.of(2, 1);
    private static final List<Integer> FORWARD_LEFT_DIAGONAL = List.of(2, -1);
    private static final List<Integer> RIGHT_RIGHT_DIAGONAL = List.of(-1, 2);
    private static final List<Integer> RIGHT_LEFT_DIAGONAL = List.of(1, 2);
    private static final List<Integer> LEFT_RIGHT_DIAGONAL = List.of(1, -2);
    private static final List<Integer> LEFT_LEFT_DIAGONAL = List.of(-1, -2);
    private static final List<Integer> BACKWARD_RIGHT_DIAGONAL = List.of(-2, 1);
    private static final List<Integer> BACKWARD_LEFT_DIAGONAL = List.of(-2, -1);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            FORWARD_RIGHT_DIAGONAL, FORWARD_LEFT_DIAGONAL,
            RIGHT_RIGHT_DIAGONAL, RIGHT_LEFT_DIAGONAL,
            LEFT_RIGHT_DIAGONAL, LEFT_LEFT_DIAGONAL,
            BACKWARD_RIGHT_DIAGONAL, BACKWARD_LEFT_DIAGONAL
    );

    public Ma(TeamType teamType) {
        super(PieceType.MA, teamType);
    }

    @Override
    int calculatePathY(Position arrivalPosition, int differenceForY, int differenceForX,
                       List<Position> positions, int currentY, int currentX) {
        if (isNotStartDirection(differenceForY)) {
            return currentY;
        }
        int differenceUnitY = calculateUnit(differenceForY);
        currentY += differenceUnitY;
        positions.add(Position.valueOf(currentY, currentX));
        positions.add(arrivalPosition);
        return currentY;
    }

    @Override
    void calculatePathX(Position arrivalPosition, int differenceForY, int differenceForX,
                        List<Position> positions, int currentY, int currentX) {
        if (isNotStartDirection(differenceForX)) {
            return;
        }
        int differenceUnitX = calculateUnit(differenceForX);
        currentX += differenceUnitX;
        positions.add(Position.valueOf(currentY, currentX));
        positions.add(arrivalPosition);
    }

    @Override
    void validateDistanceAndDirection(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 말은 직선 1칸 이동 후 대각선 1칸으로만 이동할 수 있습니다.");
        }
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !AVAILABLE_DIFFERENCE.contains(List.of(differenceForY, differenceForX));
    }

    private boolean isNotStartDirection(final int difference) {
        return Math.abs(difference) != 2;
    }
}
