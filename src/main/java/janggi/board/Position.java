package janggi.board;

import java.util.List;
import java.util.stream.IntStream;

public record Position(int x, int y) {

    private static final int BOARD_X_LOWER_BOUND = 1;
    private static final int BOARD_X_UPPER_BOUND = 9;
    private static final int BOARD_Y_LOWER_BOUND = 1;
    private static final int BOARD_Y_UPPER_BOUND = 10;

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

    public Position offset(final int offsetX, final int offsetY) {
        return new Position(x + offsetX, y + offsetY);
    }

    public boolean isHorizontalMove(final Position other) {
        return deltaX(other) != 0 && deltaY(other) == 0;
    }

    public boolean isVerticalMove(final Position other) {
        return deltaX(other) == 0 && deltaY(other) != 0;
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
}
