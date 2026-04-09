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

    public static PalaceTopology from() {
        return new PalaceTopology(Map.of(
                new Position(1, 4), List.of(
                        List.of(Movement.DOWN_RIGHT),
                        List.of(Movement.DOWN_RIGHT, Movement.DOWN_RIGHT)
                ),
                new Position(1, 6), List.of(
                        List.of(Movement.DOWN_LEFT),
                        List.of(Movement.DOWN_LEFT, Movement.DOWN_LEFT)
                ),
                new Position(2, 5), List.of(
                        List.of(Movement.UP_LEFT),
                        List.of(Movement.UP_RIGHT),
                        List.of(Movement.DOWN_LEFT),
                        List.of(Movement.DOWN_RIGHT)
                ),
                new Position(3, 4), List.of(
                        List.of(Movement.UP_RIGHT),
                        List.of(Movement.UP_RIGHT, Movement.UP_RIGHT)
                ),
                new Position(3, 6), List.of(
                        List.of(Movement.UP_LEFT),
                        List.of(Movement.UP_LEFT, Movement.UP_LEFT)
                ),
                new Position(8, 4), List.of(
                        List.of(Movement.DOWN_RIGHT),
                        List.of(Movement.DOWN_RIGHT, Movement.DOWN_RIGHT)
                ),
                new Position(8, 6), List.of(
                        List.of(Movement.DOWN_LEFT),
                        List.of(Movement.DOWN_LEFT, Movement.DOWN_LEFT)
                ),
                new Position(9, 5), List.of(
                        List.of(Movement.UP_LEFT),
                        List.of(Movement.UP_RIGHT),
                        List.of(Movement.DOWN_LEFT),
                        List.of(Movement.DOWN_RIGHT)
                ),
                new Position(10, 4), List.of(
                        List.of(Movement.UP_RIGHT),
                        List.of(Movement.UP_RIGHT, Movement.UP_RIGHT)
                ),
                new Position(10, 6), List.of(
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
