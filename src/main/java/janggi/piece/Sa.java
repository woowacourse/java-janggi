package janggi.piece;

import janggi.Team.Team;
import janggi.position.Position;
import java.util.List;

public class Sa extends Piece {

    public Sa(Team team) {
        super(PieceType.SA, team);
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
        if (Math.abs(differenceForY) + Math.abs(differenceForX) != 1) {
            throw new IllegalArgumentException("[ERROR] 사는 한 방향으로 한 칸만 이동할 수 있습니다.");
        }
    }

    private int calculateUnit(int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }
}
