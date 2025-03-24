package janggi.piece;

import janggi.position.Path;
import janggi.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(PieceType.CANNON, team);
    }

    @Override
    protected void validatePath(final Map<Position, Piece> pieces, final Path path) {
        if (computeCountExistPieceExceptLast(path, pieces) != 1) {
            throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }
        if (hasCannon(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
        }
    }

    @Override
    protected int moveY(Position arrivalPosition, int differenceForY, final int differenceForX, int currentY, List<Position> positions,
                      int currentX) {
        int differenceUnitY = calculateUnit(differenceForY);
        while (currentY != arrivalPosition.getY()) {
            currentY += differenceUnitY;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentY;
    }

    @Override
    protected int moveX(Position arrivalPosition, final int differenceForY, int differenceForX, int currentX, List<Position> positions,
                      int currentY) {
        int differenceUnitX = calculateUnit(differenceForX);
        while (currentX != arrivalPosition.getX()) {
            currentX += differenceUnitX;
            positions.add(Position.valueOf(currentY, currentX));
        }
        return currentX;
    }

    @Override
    protected void validateMove(int differenceForY, int differenceForX) {
        if (doesNotMoveInRange(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 포는 한 방향으로만 이동할 수 있습니다.");
        }
    }

    private int computeCountExistPieceExceptLast(final Path path, final Map<Position, Piece> pieces) {
        List<Position> positions = new ArrayList<>(path.getPositions());
        positions.removeLast();

        return (int) positions.stream()
                .filter(pieces::containsKey)
                .count();
    }

    private boolean hasCannon(final Path path, final Map<Position, Piece> pieces) {
        return path.getPositions().stream()
                .filter(pieces::containsKey)
                .map(pieces::get)
                .anyMatch(piece -> piece.matchPieceType(PieceType.CANNON));
    }

    private boolean doesNotMoveInRange(int differenceForY, int differenceForX) {
        return !((Math.abs(differenceForY) > 0 && Math.abs(differenceForX) == 0) ||
                (Math.abs(differenceForY) == 0 && Math.abs(differenceForX) > 0));
    }
}
