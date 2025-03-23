package janggi.board;

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

    public Position moveHorizontal(final int step) {
        if (step > 0) {
            return up(step);
        }
        return down(step);
    }

    public Position moveVertical(final int step) {
        if (step > 0) {
            return right(step);
        }
        return left(step);
    }

    public boolean isHorizontalMove(final Position other) {
        int changeXValue = this.x - other.x;
        int changeYValue = this.y - other.y;
        return changeXValue != 0 && changeYValue == 0;
    }

    public boolean isVerticalMove(final Position other) {
        int changeXValue = this.x - other.x;
        int changeYValue = this.y - other.y;
        return changeXValue == 0 && changeYValue != 0;
    }
}
