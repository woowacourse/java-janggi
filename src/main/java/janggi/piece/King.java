package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class King extends Piece {

    private static final int KING_MOVE_DISTANCE = 1;

    public King(Team team) {
        super(PieceType.KING, team);
    }

    @Override
    protected void validateMove(int differenceForY, int differenceForX) {
        if (doesNotMoveInRange(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 왕은 한 방향으로 한 칸만 이동할 수 있습니다.");
        }
    }

    @Override
    protected int moveY(Position arrivalPosition, int differenceForY, int differenceForX, int currentY, List<Position> positions,
                        int currentX) {
        int differenceUnitY = calculateUnit(differenceForY);
        while (currentY != arrivalPosition.getY()) {
            currentY += differenceUnitY;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentY;
    }

    @Override
    protected int moveX(Position arrivalPosition, int differenceForY, int differenceForX, int currentX, List<Position> positions,
                        int currentY) {
        int differenceUnitX = calculateUnit(differenceForX);
        while (currentX != arrivalPosition.getX()) {
            currentX += differenceUnitX;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentX;
    }

    @Override
    protected void validatePath(final Map<Position, Piece> pieces, final Path path) {
        if (hasPieceInMiddle(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    private boolean doesNotMoveInRange(final int differenceForY, final int differenceForX) {
        return Math.abs(differenceForY) + Math.abs(differenceForX) != KING_MOVE_DISTANCE;
    }
}
