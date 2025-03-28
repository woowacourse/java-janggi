package janggi.domain.board;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public record Position(int x, int y) {

    private static final int BOARD_X_LOWER_BOUND = 1;
    private static final int BOARD_X_UPPER_BOUND = 9;
    private static final int BOARD_Y_LOWER_BOUND = 1;
    private static final int BOARD_Y_UPPER_BOUND = 10;
    private static final Set<Position> CAN_MOVE_DIAGONAL_IN_PALACE_POSITIONS = Set.of(
            // Blue Team Palace
            new Position(4, 1),
            new Position(4, 3),
            new Position(5, 2),
            new Position(6, 1),
            new Position(6, 3),

            // Red Team Palace
            new Position(4, 8),
            new Position(4, 10),
            new Position(5, 9),
            new Position(6, 8),
            new Position(6, 10)
    );

    public Position {
        validateBoardBound(x, y);
    }

    private void validateBoardBound(final int x, final int y) {
        if (x < BOARD_X_LOWER_BOUND || x > BOARD_X_UPPER_BOUND || y < BOARD_Y_LOWER_BOUND || y > BOARD_Y_UPPER_BOUND) {
            throw new IllegalArgumentException("장기판 밖으로는 이동할 수 없습니다.");
        }
    }

    public Position up(final int step) {
        return new Position(x, y + step);
    }

    public Position down(final int step) {
        return new Position(x, y - step);
    }

    public Position left(final int step) {
        return new Position(x - step, y);
    }

    public Position right(final int step) {
        return new Position(x + step, y);
    }

    public Position upRight(final int step) {
        return new Position(x + step, y + step);
    }

    public Position upLeft(final int step) {
        return new Position(x - step, y + step);
    }

    public Position downLeft(final int step) {
        return new Position(x - step, y - step);
    }

    public Position downRight(final int step) {
        return new Position(x + step, y - step);
    }

    public Position offset(final int offsetX, final int offsetY) {
        return new Position(x + offsetX, y + offsetY);
    }

    public boolean isHorizontalMove(final Position other) {
        return deltaX(other) != 0 && deltaY(other) == 0;
    }

    public boolean isVerticalMove(final Position other) {
        return deltaX(other) == 0 && deltaY(other) != 0;
    }

    public boolean isDiagonalMove(final Position other) {
        return absDeltaX(other) == absDeltaY(other);
    }

    public boolean isUp(final Position other) {
        return deltaY(other) > 0;
    }

    public boolean isDown(final Position other) {
        return deltaY(other) < 0;
    }

    public boolean isLeft(final Position other) {
        return deltaX(other) < 0;
    }

    public boolean isRight(final Position other) {
        return deltaX(other) > 0;
    }

    public boolean isUpRight(final Position other) {
        return deltaX(other) > 0 && deltaY(other) > 0;
    }

    public boolean isUpLeft(final Position other) {
        return deltaX(other) < 0 && deltaY(other) > 0;
    }

    public boolean isDownRight(final Position other) {
        return deltaX(other) > 0 && deltaY(other) < 0;
    }

    public boolean isDownLeft(final Position other) {
        return deltaX(other) < 0 && deltaY(other) < 0;
    }

    public boolean isOneStep(final Position other) {
        return absDeltaX(other) <= 1 && absDeltaY(other) <= 1;
    }

    public int deltaX(final Position other) {
        return other.x - this.x;
    }

    public int deltaY(final Position other) {
        return other.y - this.y;
    }

    public int absDeltaX(final Position other) {
        return Math.abs(deltaX(other));
    }

    public int absDeltaY(final Position other) {
        return Math.abs(deltaY(other));
    }

    public List<Position> horizontalPath(final Position other) {
        if (this.isRight(other)) {
            return IntStream.range(1, this.absDeltaX(other))
                    .mapToObj(this::right)
                    .toList();
        }
        return IntStream.range(1, this.absDeltaX(other))
                .mapToObj(this::left)
                .toList();
    }

    public List<Position> verticalPath(final Position other) {
        if (this.isUp(other)) {
            return IntStream.range(1, this.absDeltaY(other))
                    .mapToObj(this::up)
                    .toList();
        }
        return IntStream.range(1, this.absDeltaY(other))
                .mapToObj(this::down)
                .toList();
    }

    public List<Position> diagonalPath(final Position other) {
        int step = this.absDeltaX(other);
        if (isUpRight(other)) {
            return IntStream.range(1, step)
                    .mapToObj(this::upRight)
                    .toList();
        }
        if (isUpLeft(other)) {
            return IntStream.range(1, step)
                    .mapToObj(this::upLeft)
                    .toList();
        }
        if (isDownRight(other)) {
            return IntStream.range(1, step)
                    .mapToObj(this::downRight)
                    .toList();
        }
        return IntStream.range(1, step)
                .mapToObj(this::downLeft)
                .toList();
    }

    public boolean isInPalace() {
        return (x >= 4 && x <= 6) && ((y >= 1 && y <= 3) || (y >= 8 && y <= 10));
    }

    public boolean isMoveInPalace(final Position other) {
        return this.isInPalace() && other.isInPalace();
    }

    public boolean isMoveDiagonalInPalace(final Position other) {
        return isMoveInPalace(other) && isDiagonalMove(other) && canMoveDiagonalPositionInPalace();
    }

    public boolean canMoveDiagonalPositionInPalace() {
        return CAN_MOVE_DIAGONAL_IN_PALACE_POSITIONS.contains(this);
    }
}
