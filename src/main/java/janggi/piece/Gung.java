package janggi.piece;

import janggi.Team.Team;
import janggi.position.Position;
import java.util.List;
import java.util.Set;

public class Gung extends Piece {

    private static final List<Integer> FORWARD = List.of(-1, 0);
    private static final List<Integer> RIGHT = List.of(0, 1);
    private static final List<Integer> LEFT = List.of(0, -1);
    private static final List<Integer> BACKWARD = List.of(1, 0);

    private static final Set<List<Integer>> AVAILABLE_DIFFERENCE = Set.of(
            FORWARD, RIGHT, LEFT, BACKWARD
    );

    public Gung(Team team) {
        super(PieceType.GUNG, team);
    }

    @Override
    public int calculatePathY(Position arrivalPosition, int differenceForY, int differenceForX,
                              List<Position> positions, int currentY, int currentX) {
        int differenceUnitY = calculateUnit(differenceForY);
        while (currentY != arrivalPosition.getY()) {
            currentY += differenceUnitY;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentY;
    }

    @Override
    public int calculatePathX(Position arrivalPosition, int differenceForY, int differenceForX,
                              List<Position> positions, int currentY, int currentX) {
        int differenceUnitX = calculateUnit(differenceForX);
        while (currentX != arrivalPosition.getX()) {
            currentX += differenceUnitX;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentX;
    }

    @Override
    public void validateMove(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 궁은 한 방향으로 한 칸만 이동할 수 있습니다.");
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
}
