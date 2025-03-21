package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Byeong extends Piece {

    private static final int BYEONG_MOVE_DISTANCE = 1;

    public Byeong() {
        super(PieceType.BYEONG, Team.HAN);
    }

    @Override
    public Path makePath(Position currentPosition, Position arrivalPosition, final Map<Position, Piece> pieces) {
        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        validateMove(differenceForY, differenceForX);

        final List<Position> positions = new ArrayList<>();
        int currentY = currentPosition.getY();
        int currentX = currentPosition.getX();
        currentY = moveY(arrivalPosition, differenceForY, currentY, positions, currentX);
        moveX(arrivalPosition, differenceForX, currentX, positions, currentY);
        Path path = new Path(positions);
        if (hasPieceInMiddle(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
        return path;
    }

    private boolean hasPieceInMiddle(final Path path, final Map<Position, Piece> pieces) {
        List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();
        return positions.stream()
                .anyMatch(pieces::containsKey);
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
        if (canNotMoveBackward(differenceForY) || Math.abs(differenceForY) + Math.abs(differenceForX) > BYEONG_MOVE_DISTANCE) {
            throw new IllegalArgumentException("[ERROR] 병은 앞, 좌, 우로 한 칸 씩만 이동할 수 있습니다.");
        }
    }

    private boolean canNotMoveBackward(int differenceForY) {
        return differenceForY < 0;
    }
}
