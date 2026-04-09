package janggi.domain;

import java.util.List;
import java.util.Map;

public class PalaceTopology {
    private static final int PALACE_HAN_START_ROW = 1;
    private static final int PALACE_HAN_START_COL = 4;
    private static final int PALACE_HAN_END_ROW = 3;
    private static final int PALACE_HAN_END_COL = 6;

    private static final int PALACE_CHO_START_ROW = 8;
    private static final int PALACE_CHO_START_COL = 4;
    private static final int PALACE_CHO_END_ROW = 10;
    private static final int PALACE_CHO_END_COL = 6;
    private final Map<Position, List<List<Movement>>> diagonalMovements;

    public PalaceTopology(Map<Position, List<List<Movement>>> diagonalMovements) {
        this.diagonalMovements = diagonalMovements;
    }

    private static final Position HAN_PALACE_TOP_LEFT = new Position(1, 4);
    private static final Position HAN_PALACE_TOP_RIGHT = new Position(1, 6);
    private static final Position HAN_PALACE_CENTER = new Position(2, 5);
    private static final Position HAN_PALACE_BOTTOM_LEFT = new Position(3, 6);
    private static final Position HAN_PALACE_BOTTOM_RIGHT = new Position(3, 6);

    private static final Position CHO_PALACE_TOP_LEFT = new Position(8, 4);
    private static final Position CHO_PALACE_TOP_RIGHT = new Position(8, 6);
    private static final Position CHO_PALACE_CENTER = new Position(9, 5);
    private static final Position CHO_PALACE_BOTTOM_LEFT = new Position(10, 4);
    private static final Position CHO_PALACE_BOTTOM_RIGHT = new Position(10, 6);

    public static PalaceTopology from() {
        return new PalaceTopology(Map.of(
                HAN_PALACE_TOP_LEFT, List.of(
                        List.of(Movement.DOWN_RIGHT),
                        List.of(Movement.DOWN_RIGHT, Movement.DOWN_RIGHT)
                ),
                HAN_PALACE_TOP_RIGHT, List.of(
                        List.of(Movement.DOWN_LEFT),
                        List.of(Movement.DOWN_LEFT, Movement.DOWN_LEFT)
                ),
                HAN_PALACE_CENTER, List.of(
                        List.of(Movement.UP_LEFT),
                        List.of(Movement.UP_RIGHT),
                        List.of(Movement.DOWN_LEFT),
                        List.of(Movement.DOWN_RIGHT)
                ),
                HAN_PALACE_BOTTOM_LEFT, List.of(
                        List.of(Movement.UP_RIGHT),
                        List.of(Movement.UP_RIGHT, Movement.UP_RIGHT)
                ),
                HAN_PALACE_BOTTOM_RIGHT, List.of(
                        List.of(Movement.UP_LEFT),
                        List.of(Movement.UP_LEFT, Movement.UP_LEFT)
                ),
                CHO_PALACE_TOP_LEFT, List.of(
                        List.of(Movement.DOWN_RIGHT),
                        List.of(Movement.DOWN_RIGHT, Movement.DOWN_RIGHT)
                ),
                CHO_PALACE_TOP_RIGHT, List.of(
                        List.of(Movement.DOWN_LEFT),
                        List.of(Movement.DOWN_LEFT, Movement.DOWN_LEFT)
                ),
                CHO_PALACE_CENTER, List.of(
                        List.of(Movement.UP_LEFT),
                        List.of(Movement.UP_RIGHT),
                        List.of(Movement.DOWN_LEFT),
                        List.of(Movement.DOWN_RIGHT)
                ),
                CHO_PALACE_BOTTOM_LEFT, List.of(
                        List.of(Movement.UP_RIGHT),
                        List.of(Movement.UP_RIGHT, Movement.UP_RIGHT)
                ),
                CHO_PALACE_BOTTOM_RIGHT, List.of(
                        List.of(Movement.UP_LEFT),
                        List.of(Movement.UP_LEFT, Movement.UP_LEFT)
                )
        ));
    }

    public List<List<Movement>> diagonalOneStepMovements(Position position) {
        return diagonalMovements.getOrDefault(position, List.of()).stream()
                .filter(movements -> movements.size() == 1)
                .toList();
    }

    public List<List<Movement>> diagonalLineMovements(Position position) {
        return diagonalMovements.getOrDefault(position, List.of());
    }

    public boolean isPalace(Position position) {
        return isHanPalace(position) || isChoPalace(position);
    }

    public boolean isHanPalace(Position position) {
        return PALACE_HAN_START_ROW <= position.x() && position.x() <= PALACE_HAN_END_ROW
                && PALACE_HAN_START_COL <= position.y() && position.y() <= PALACE_HAN_END_COL;
    }

    public boolean isChoPalace(Position position) {
        return PALACE_CHO_START_ROW <= position.x() && position.x() <= PALACE_CHO_END_ROW
                && PALACE_CHO_START_COL <= position.y() && position.y() <= PALACE_CHO_END_COL;
    }
}
