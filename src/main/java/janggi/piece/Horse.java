package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Horse extends Piece {

    private static final int HORSE_UNIT_DISTANCE = 2;
    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            List.of(HORSE_UNIT_DISTANCE, 1), List.of(
                    HORSE_UNIT_DISTANCE, -1), List.of(-HORSE_UNIT_DISTANCE, 1), List.of(-HORSE_UNIT_DISTANCE, -1),
            List.of(1, HORSE_UNIT_DISTANCE), List.of(1, -HORSE_UNIT_DISTANCE), List.of(-1,
                    HORSE_UNIT_DISTANCE), List.of(-1, -HORSE_UNIT_DISTANCE));

    public Horse(Team team) {
        super(PieceType.HORSE, team);
    }

    @Override
    protected void validateMove(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 말은 직선 1칸 이동 후 대각선 1칸으로만 이동할 수 있습니다.");
        }
    }

    @Override
    protected int moveY(Position arrivalPosition, int differenceForY, int differenceForX, int currentY,
                        List<Position> positions, int currentX) {
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
    protected int moveX(Position arrivalPosition, int differenceForY, int differenceForX, int currentX,
                        List<Position> positions, int currentY) {
        if (isNotStartDirection(differenceForX)) {
            return currentX;
        }
        int differenceUnitX = calculateUnit(differenceForX);
        currentX += differenceUnitX;
        positions.add(Position.valueOf(currentY, currentX));
        positions.add(arrivalPosition);
        return currentX;
    }

    @Override
    protected void validatePath(final Map<Position, Piece> pieces, final Path path) {
        if (hasPieceInMiddle(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private boolean hasPieceInMiddle(final Path path, final Map<Position, Piece> pieces) {
        List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(pieces::containsKey);
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
        return Math.abs(difference) != HORSE_UNIT_DISTANCE;
    }
}
