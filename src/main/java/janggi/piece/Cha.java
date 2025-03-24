package janggi.piece;

import janggi.Team.Team;
import janggi.position.Position;
import java.util.List;

public class Cha extends Piece {

    public Cha(Team team) {
        super(PieceType.CHA, team);
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
            throw new IllegalArgumentException("[ERROR] 차는 한 방향으로만 이동할 수 있습니다.");
        }
    }

    private int calculateUnit(int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !((Math.abs(differenceForY) > 0 && Math.abs(differenceForX) == 0) ||
                (Math.abs(differenceForY) == 0 && Math.abs(differenceForX) > 0));
    }
}
