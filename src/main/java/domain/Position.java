package domain;

import static constant.BoardConstant.*;
import static constant.ErrorMessage.INVALID_POSITION_RANGE;

public class Position {

    private static final Position[][] CACHE = new Position[MAX_X + 1][MAX_Y + 1];
    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        validateOutOfRange(x, y);
        if (CACHE[x][y] == null) {
            CACHE[x][y] = new Position(x, y);
        }
        return CACHE[x][y];
    }

    public boolean canMove(int dx, int dy) {
        int nx = x + dx;
        int ny = y + dy;
        return MIN_X <= nx && nx <= MAX_X && MIN_Y <= ny && ny <= MAX_Y;
    }

    public Position createPosition(int dx, int dy) {
        return Position.of(x + dx, y + dy);
    }

    public boolean isDiagonalTo(Position target) {
        int dx = Math.abs(target.x - this.x);
        int dy = Math.abs(target.y - this.y);
        return dx == dy && dx > 0;
    }

    public Direction directionTo(Position target) {
        int dx = Integer.signum(target.x - this.x);
        int dy = Integer.signum(target.y - this.y);
        return Direction.of(dx, dy);
    }

    public String createStoreKey() {
        return x + " " + y;
    }

    private static void validateOutOfRange(int x, int y) {
        if(!((MIN_X <= x && x <= MAX_X) && (MIN_Y <= y && y <= MAX_Y))) {
            throw new IllegalArgumentException(INVALID_POSITION_RANGE);
        }
    }
}