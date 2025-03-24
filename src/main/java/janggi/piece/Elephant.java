package janggi.piece;

import static janggi.piece.direction.Direction.DOWN;
import static janggi.piece.direction.Direction.DOWN_LEFT;
import static janggi.piece.direction.Direction.DOWN_RIGHT;
import static janggi.piece.direction.Direction.LEFT;
import static janggi.piece.direction.Direction.RIGHT;
import static janggi.piece.direction.Direction.UP;
import static janggi.piece.direction.Direction.UP_LEFT;
import static janggi.piece.direction.Direction.UP_RIGHT;

import janggi.piece.direction.Movement;
import janggi.position.Path;
import janggi.position.Position;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    private static final List<Movement> MOVEMENTS = List.of(
            new Movement(DOWN, DOWN_RIGHT, DOWN_RIGHT), new Movement(DOWN, DOWN_LEFT, DOWN_LEFT),
            new Movement(UP, UP_RIGHT, UP_RIGHT), new Movement(UP, UP_LEFT, UP_LEFT),
            new Movement(RIGHT, DOWN_RIGHT, DOWN_RIGHT), new Movement(LEFT, DOWN_LEFT, DOWN_LEFT),
            new Movement(RIGHT, UP_RIGHT, UP_RIGHT), new Movement(LEFT, UP_LEFT, UP_LEFT)

    );

    public Elephant(Team team) {
        super(PieceType.ELEPHANT, team);
    }

    @Override
    protected void validatePath(final Map<Position, Piece> pieces, final Path path) {
        if (hasPieceInMiddle(path, pieces)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재하여 이동할 수 없습니다.");
        }
    }

    @Override
    protected int moveY(final Position arrivalPosition, final int differenceForY, final int differenceForX,
                        int currentY, final List<Position> positions, int currentX) {
        if (isNotStartDirection(differenceForY)) {
            return currentY;
        }
        int differenceUnitY = calculateUnit(differenceForY);
        currentY += differenceUnitY;
        positions.add(Position.valueOf(currentY, currentX));

        int differenceUnitX = calculateUnit(differenceForX);
        currentY += differenceUnitY;
        currentX += differenceUnitX;
        positions.add(Position.valueOf(currentY, currentX));

        positions.add(arrivalPosition);
        return currentY;
    }

    @Override
    protected int moveX(final Position arrivalPosition, final int differenceForY, final int differenceForX,
                        int currentX, final List<Position> positions, int currentY) {
        if (isNotStartDirection(differenceForX)) {
            return currentX;
        }
        int differenceUnitX = calculateUnit(differenceForX);
        currentX += differenceUnitX;
        positions.add(Position.valueOf(currentY, currentX));

        int differenceUnitY = calculateUnit(differenceForY);
        currentY += differenceUnitY;
        currentX += differenceUnitX;
        positions.add(Position.valueOf(currentY, currentX));

        positions.add(arrivalPosition);
        return currentX;
    }

    @Override
    protected void validateMove(final int differenceForY, final int differenceForX) {
        if (isInValidMovement(MOVEMENTS, differenceForY, differenceForX)) {
            throw new IllegalArgumentException("[ERROR] 상은 직선 1칸 이동 후 대각선 2칸으로만 이동할 수 있습니다.");
        }
    }

    private boolean isNotStartDirection(final int difference) {
        return Math.abs(difference) != 3;
    }
}
