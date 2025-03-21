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
    public Path makePath(final Position currentPosition, final Position arrivalPosition,
                         final Map<Position, Piece> pieces) {

        int differenceForY = arrivalPosition.calculateDifferenceForY(currentPosition);
        int differenceForX = arrivalPosition.calculateDifferenceForX(currentPosition);

        validateMove(differenceForY, differenceForX);

        final List<Position> positions = new ArrayList<>();
        int currentY = currentPosition.getY();
        int currentX = currentPosition.getX();
        currentY = moveY(arrivalPosition, differenceForY, currentY, positions, currentX);
        moveX(arrivalPosition, differenceForX, currentX, positions, currentY);

        Path path = new Path(positions);
        if (computeCountExistPieceExceptLast(path, pieces) != 1) {
            throw new IllegalArgumentException("[ERROR] 오직 하나의 기물만 뛰어넘을 수 있습니다.");
        }
        if (hasCannon(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 포는 포끼리 뛰어넘거나 잡을 수 없습니다.");
        }
        return path;
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

    private void validateMove(int differenceForY, int differenceForX) {
        if (canNotMove(differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 포는 한 방향으로만 이동할 수 있습니다.");
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
