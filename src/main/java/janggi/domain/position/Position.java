package janggi.domain.position;

import janggi.domain.board.Board;
import janggi.exception.ErrorException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record Position(int x, int y) {

    private static final Map<Integer, Position> POSITION_CACHE = new HashMap<>();

    static {
        for (int y = Board.MIN_ROW; y < Board.MAX_ROW; y++) {
            for (int x = Board.MIN_COLUMN; x < Board.MAX_COLUMN; x++) {
                POSITION_CACHE.put(convertToKey(x, y), new Position(x, y));
            }
        }
    }

    private static int convertToKey(int x, int y) {
        return Objects.hash(x, y);
    }

    public static Position of(int x, int y) {
        int key = convertToKey(x, y);
        if (!POSITION_CACHE.containsKey(key)) {
            throw new ErrorException("9x10 크기의 장기 보드판의 좌표값만 가능합니다.");
        }
        return POSITION_CACHE.get(key);
    }

    public boolean isHorizontalTo(Position otherPosition) {
        return this.y == otherPosition.y;
    }

    public boolean isVerticalTo(Position otherPosition) {
        return this.x == otherPosition.x;
    }

    public int calculateXDistanceTo(Position otherPosition) {
        return Math.abs(this.x - otherPosition.x);
    }

    public int calculateYDistance(Position otherPosition) {
        return Math.abs(this.y - otherPosition.y);
    }
}
