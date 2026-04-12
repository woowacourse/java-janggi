package view;

import domain.Position;
import java.util.List;

public enum EmptyStateFormatter {
    PALACE_HORIZONTAL(List.of(new Position(3, 1), new Position(5, 1), new Position(3, 8), new Position(5, 8)), "一"),
    PALACE_RIGHT_UP(List.of(new Position(3, 0), new Position(5, 2), new Position(3, 7), new Position(5, 9)), "⟋"),
    PALACE_LEFT_UP(List.of(new Position(3, 2), new Position(5, 0), new Position(3, 9), new Position(5, 7)), "⟍"),
    PALACE_VERTICAL(List.of(new Position(4, 0), new Position(4, 2), new Position(4, 7), new Position(4, 9)), "ㅣ"),
    ;

    private static final String BASIC_EMPTY_STATE = "十";

    private final List<Position> positions;
    private final String palaceType;

    EmptyStateFormatter(List<Position> positions, String palaceType) {
        this.positions = positions;
        this.palaceType = palaceType;
    }

    public static String getEmptyState(Position position) {
        if (PALACE_HORIZONTAL.positions.contains(position)) {
            return PALACE_HORIZONTAL.palaceType;
        }
        if (PALACE_RIGHT_UP.positions.contains(position)) {
            return PALACE_RIGHT_UP.palaceType;
        }
        if (PALACE_LEFT_UP.positions.contains(position)) {
            return PALACE_LEFT_UP.palaceType;
        }
        if (PALACE_VERTICAL.positions.contains(position)) {
            return PALACE_VERTICAL.palaceType;
        }
        return BASIC_EMPTY_STATE;
    }
}
