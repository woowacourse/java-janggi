package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import janggi.view.PieceType;
import java.util.ArrayList;
import java.util.List;

public class Byeong extends Piece {

    public Byeong() {
        super(PieceType.BYEONG, Team.HAN);
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition) {
        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        validateMove(differenceForY, differenceForX);

        final List<Position> positions = new ArrayList<>();
        int currentY = currentPosition.getY();
        int currentX = currentPosition.getX();
        currentY = moveY(arrivalPosition, differenceForY, currentY, positions, currentX);
        moveX(arrivalPosition, differenceForX, currentX, positions, currentY);
        return new Path(positions);
    }

    private int moveY(Position arrivalPosition, int differenceForY, int currentY, List<Position> positions,
                      int currentX) {
        int differenceUnitY = calculateUnit(differenceForY);
        while (currentY != arrivalPosition.getY()) {
            currentY += differenceUnitY;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentY;
    }

    private int moveX(Position arrivalPosition, int differenceForX, int currentX, List<Position> positions,
                      int currentY) {
        int differenceUnitX = calculateUnit(differenceForX);
        while (currentX != arrivalPosition.getX()) {
            currentX += differenceUnitX;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentX;
    }

    private int calculateUnit(int difference) {
        if (difference == 0) {
            return difference;
        }
        return difference / Math.abs(difference);
    }

    private void validateMove(int differenceForY, int differenceForX) {
        if (canNotMoveBackward(differenceForY) || Math.abs(differenceForY) + Math.abs(differenceForX) > 1) {
            throw new IllegalArgumentException("[ERROR] 병은 앞, 좌, 우로 한 칸 씩만 이동할 수 있습니다.");
        }
    }

    private boolean canNotMoveBackward(int differenceForY) {
        return differenceForY < 0;
    }
}
