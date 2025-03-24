package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(PieceType.CHARIOT, team);
    }

    @Override
    protected void validateMove(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 차는 한 방향으로만 이동할 수 있습니다.");
        }
    }

    @Override
    protected int moveY(Position arrivalPosition, int differenceForY, final int differenceForX, int currentY,
                        List<Position> positions,
                        int currentX) {
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

    private boolean canNotMove(int differenceForY, int differenceForX) {
        return !((Math.abs(differenceForY) > 0 && Math.abs(differenceForX) == 0) ||
                (Math.abs(differenceForY) == 0 && Math.abs(differenceForX) > 0));
    }
}
