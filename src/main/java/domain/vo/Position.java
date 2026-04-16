package domain.vo;

import constant.BoardSpec;
import java.util.HashMap;
import java.util.Map;

public class Position {

    private static final String INVALID_POSITION_RANGE = String.format("x 좌표는 %d~%d, y 좌표는 %d~%d, 사이여야 합니다.",
        BoardSpec.MIN_X, BoardSpec.MAX_X, BoardSpec.MIN_Y, BoardSpec.MAX_Y);

    private static final Map<Integer, Map<Integer, Position>> CACHE = new HashMap<>();
    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        validateOutOfRange(x, y);
        return CACHE
            .computeIfAbsent(x, k -> new HashMap<>())
            .computeIfAbsent(y, k -> new Position(x, y));
    }

    public boolean canMove(int dx, int dy) {
        int nx = x + dx;
        int ny = y + dy;
        return BoardSpec.MIN_X <= nx && nx <= BoardSpec.MAX_X && BoardSpec.MIN_Y <= ny && ny <= BoardSpec.MAX_Y;
    }

    public Position createPosition(int dx, int dy) {
        return Position.of(x + dx, y + dy);
    }

    private static void validateOutOfRange(int x, int y) {
        if (!((BoardSpec.MIN_X <= x && x <= BoardSpec.MAX_X) && (BoardSpec.MIN_Y <= y && y <= BoardSpec.MAX_Y))) {
            throw new IllegalArgumentException(INVALID_POSITION_RANGE);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}