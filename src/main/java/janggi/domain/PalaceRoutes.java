package janggi.domain;

import java.util.List;
import java.util.Map;

public class PalaceRoutes {
    private static final String UTILITY_CLASS_INSTANTIATION_MESSAGE = "PalaceRoutes는 유틸리티 클래스이므로 인스턴스화할 수 없습니다.";
    private static final Map<Position, List<Movement>> DIAGONAL_ONE_STEP = Map.of(
            new Position(2, 5), List.of(Movement.UP_LEFT, Movement.UP_RIGHT, Movement.DOWN_LEFT, Movement.DOWN_RIGHT),
            new Position(9, 5), List.of(Movement.UP_LEFT, Movement.UP_RIGHT, Movement.DOWN_LEFT, Movement.DOWN_RIGHT),
            new Position(1, 4), List.of(Movement.DOWN_RIGHT),
            new Position(1, 6), List.of(Movement.DOWN_LEFT),
            new Position(3, 4), List.of(Movement.UP_RIGHT),
            new Position(3, 6), List.of(Movement.UP_LEFT),
            new Position(8, 4), List.of(Movement.DOWN_RIGHT),
            new Position(8, 6), List.of(Movement.DOWN_LEFT),
            new Position(10, 4), List.of(Movement.UP_RIGHT),
            new Position(10, 6), List.of(Movement.UP_LEFT)
    );

    private PalaceRoutes() {
        throw new AssertionError(UTILITY_CLASS_INSTANTIATION_MESSAGE);
    }

    public static List<List<Movement>> diagonalOneStepMovements(Position position) {
        return DIAGONAL_ONE_STEP.getOrDefault(position, List.of()).stream().
                map(List::of)
                .toList();
    }
}