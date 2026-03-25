package janggi.domain;

import janggi.exception.BoardOutOfRangeException;

import java.util.HashMap;
import java.util.Map;

public class Position {
    private static final int MIN_X = 0;
    private static final int MAX_X = 8;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 9;

    private static final Map<String, Position> CACHE = new HashMap<>();

    static {
        for (int x = MIN_X; x <= MAX_X; x++) {
            for (int y = MIN_Y; y <= MAX_Y; y++) {
                CACHE.put(toKey(x, y), new Position(x, y));
            }
        }
    }

    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        String key = toKey(x, y);

        if (!CACHE.containsKey(key)) {
            throw new BoardOutOfRangeException();
        }

        return CACHE.get(key);
    }

    private static String toKey(int x, int y) {
        return x + "," + y;
    }
}
