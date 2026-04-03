package janggi.domain;

import java.util.List;
import java.util.Map;

public class PalaceTopology {
    private final Map<Position, List<List<Movement>>> diagonalMovements;

    public PalaceTopology(Map<Position, List<List<Movement>>> diagonalMovements) {
        this.diagonalMovements = diagonalMovements;
    }

    public static PalaceTopology from(){
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
}
