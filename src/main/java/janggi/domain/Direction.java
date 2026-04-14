package janggi.domain;

import janggi.domain.piece.Team;
import janggi.domain.vo.position.Position;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORTH(-1, 0), SOUTH(1, 0), EAST(0, 1), WEST(0, -1),
    NORTH_EAST(-1, 1), NORTH_WEST(-1, -1), SOUTH_EAST(1, 1), SOUTH_WEST(1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Direction between(Position from, Position to) {
        int rowDifference = Integer.signum(to.getRow() - from.getRow());
        int colDifference = Integer.signum(to.getCol() - from.getCol());

        return Arrays.stream(values())
                .filter(direction -> direction.match(rowDifference, colDifference))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 방향입니다."));
    }

    private boolean match(int rowDifference, int colDifference){
        return this.getDx() == rowDifference && this.getDy() == colDifference;
    }

    public static Direction forwardDirection(Team team) {
        if (team == Team.CHO) {
            return NORTH;
        }

        return SOUTH;
    }

    public static List<Direction> forwardDiagonals(Direction direction) {
        if (direction == NORTH) {
            return List.of(NORTH_EAST, NORTH_WEST);
        }

        if (direction == SOUTH) {
            return List.of(SOUTH_EAST, SOUTH_WEST);
        }

        throw  new IllegalArgumentException("NORTH 또는 SOUTH 대신 " + direction + "으로 잘못 입력되었습니다.");
    }

    public boolean isDiagonal() {
        return List.of(NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST).contains(this);
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
