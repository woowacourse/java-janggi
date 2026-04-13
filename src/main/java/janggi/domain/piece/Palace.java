package janggi.domain.piece;

import janggi.domain.position.Position;

import java.util.List;
import java.util.Map;

public class Palace {
    private static final Palace INSTANCE = new Palace();

    private static final Map<Position, List<Position>> ROUTES = Map.of(
            Position.of(1, 4), List.of(Position.of(2, 5), Position.of(3, 6)),
            Position.of(1, 6), List.of(Position.of(2, 5), Position.of(3, 4)),
            Position.of(3, 4), List.of(Position.of(2, 5), Position.of(1, 6)),
            Position.of(3, 6), List.of(Position.of(2, 5), Position.of(1, 4)),
            Position.of(2, 5), List.of(Position.of(1, 4), Position.of(1, 6),
                    Position.of(3, 4), Position.of(3, 6)),
            Position.of(8, 4), List.of(Position.of(9, 5), Position.of(10, 6)),
            Position.of(8, 6), List.of(Position.of(9, 5), Position.of(10, 4)),
            Position.of(10, 4), List.of(Position.of(9, 5), Position.of(8, 6)),
            Position.of(10, 6), List.of(Position.of(9, 5), Position.of(8, 4)),
            Position.of(9, 5), List.of(Position.of(8, 4), Position.of(8, 6),
                    Position.of(10, 4), Position.of(10, 6)));

    private Palace() {
    }

    public static Palace getInstance() {
        return INSTANCE;
    }

    public boolean isPalaceMove(Position from, Position to) {
        return (isInHanPalace(from) && isInHanPalace(to))
                || (isInChoPalace(from) && isInChoPalace(to));
    }

    public boolean hasRoute(Position from, Position to) {
        return ROUTES.containsKey(from) && ROUTES.get(from).contains(to);
    }

    private boolean isInHanPalace(Position position) {
        int row = position.getRowValue();
        int col = position.getColumnValue();
        return row >= 1 && row <= 3 && col >= 4 && col <= 6;
    }

    private boolean isInChoPalace(Position position) {
        int row = position.getRowValue();
        int col = position.getColumnValue();
        return row >= 8 && row <= 10 && col >= 4 && col <= 6;
    }
}
