package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Horse extends Piece {

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            List.of(2, 1), List.of(2, -1), List.of(-2, 1), List.of(-2, -1),
            List.of(1, 2), List.of(1, -2), List.of(-1, 2), List.of(-1, -2));

    public Horse(Team team) {
        super(PieceType.HORSE, team);
    }

    @Override
    public Path makePath(final Position currentPosition, final Position arrivalPosition) {
        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        validateMove(differenceForY, differenceForX);

        final List<Position> positions = new ArrayList<>();
        int currentY = currentPosition.getY();
        int currentX = currentPosition.getX();

        currentY = moveY(arrivalPosition, differenceForY, currentY, positions, currentX);
        moveX(arrivalPosition, differenceForX, currentY, positions, currentX);
        return new Path(positions);
    }

    private int moveY(Position arrivalPosition, int differenceForY, int currentY,
                      List<Position> positions,
                      int currentX) {
        if (isNotStartDirection(differenceForY)) {
            return currentY;
        }
        int differenceUnitY = calculateUnit(differenceForY);
        currentY += differenceUnitY;
        positions.add(Position.valueOf(currentY, currentX));
        positions.add(arrivalPosition);
        return currentY;
    }

    private int moveX(Position arrivalPosition, int differenceForX, int currentY,
                      List<Position> positions,
                      int currentX) {
        if (isNotStartDirection(differenceForX)) {
            return currentX;
        }
        int differenceUnitX = calculateUnit(differenceForX);
        currentX += differenceUnitX;
        positions.add(Position.valueOf(currentY, currentX));
        positions.add(arrivalPosition);
        return currentX;
    }

    private void validateMove(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 말은 직선 1칸 이동 후 대각선 1칸으로만 이동할 수 있습니다.");
        }
    }

    private int calculateUnit(int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !AVAILABLE_DIFFERENCE.contains(List.of(differenceForY, differenceForX));
    }

    private boolean isNotStartDirection(final int difference) {
        return Math.abs(difference) != 2;
    }
}
