package janggi.piece;

import janggi.Team.Team;
import janggi.position.Position;
import java.util.List;

public class Byeong extends Piece {

    public Byeong() {
        super(PieceType.BYEONG, Team.HAN);
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
        if (canNotMoveBackward(differenceForY) || Math.abs(differenceForY) + Math.abs(differenceForX) > 1) {
            throw new IllegalArgumentException("[ERROR] 병은 앞, 좌, 우로 한 칸 씩만 이동할 수 있습니다.");
        }
    }

    private int calculateUnit(int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

    private boolean canNotMoveBackward(int differenceForY) {
        return differenceForY < 0;
    }
}
