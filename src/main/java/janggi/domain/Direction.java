package janggi.domain;

import janggi.domain.piece.Team;
import janggi.domain.vo.Position;

public enum Direction {
    NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1),
    NORTH_EAST(-1, 1), NORTH_WEST(-1, -1), SOUTH_EAST(1, 1), SOUTH_WEST(1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction findDirection(Position from, Position to) {
        if (from.isOnSameCol(to) && from.getRow() < to.getRow()) {
            return SOUTH;
        }

        if (from.isOnSameCol(to) && from.getRow() > to.getRow()) {
            return NORTH;
        }

        if (from.isOnSameRow(to) && from.getCol() < to.getCol()) {
            return EAST;
        }

        return WEST;
    }

    public static Direction forwardDirection(Team team) {
        if (team == Team.CHO) {
            return NORTH;
        }

        return SOUTH;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
