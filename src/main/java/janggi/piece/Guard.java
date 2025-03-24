package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Guard extends Piece {

    private static final int GUARD_MOVE_DISTANCE = 1;

    public Guard(Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    protected void validateMove(int differenceForY, int differenceForX) {
        if (Math.abs(differenceForY) + Math.abs(differenceForX) != GUARD_MOVE_DISTANCE) {
            throw new IllegalArgumentException("[ERROR] 사는 한 방향으로 한 칸만 이동할 수 있습니다.");
        }
    }

    @Override
    protected int moveY(Position arrivalPosition, int differenceForY, final int differenceForX, int currentY,
                        List<Position> positions, int currentX) {
        int differenceUnitY = calculateUnit(differenceForY);
        while (currentY != arrivalPosition.getY()) {
            currentY += differenceUnitY;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentY;
    }

    @Override
    protected int moveX(Position arrivalPosition, final int differenceForY, int differenceForX, int currentX,
                        List<Position> positions,
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
}
