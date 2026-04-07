package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.piece.strategy.Direction;

import java.util.List;
import java.util.stream.IntStream;

public class Palace {

    private static final List<Position> choPalace;
    private static final List<Position> hanPalace;

    static {
        choPalace = IntStream.range(0, 3)
                .boxed()
                .flatMap(row -> IntStream.range(3, 6)
                        .mapToObj(column -> Position.of(row, column)))
                .toList();
        hanPalace = IntStream.range(7, 10)
                .boxed()
                .flatMap(row -> IntStream.range(3, 6)
                        .mapToObj(column -> Position.of(row, column)))
                .toList();
    }

    public boolean isPalace(Position position) {
        return choPalace.contains(position) || hanPalace.contains(position);
    }

    public List<Direction> calculateDirectionsByCamp(Position position) {
        if (choPalace.contains(position)) {
            return calculateDirections(position, choPalace);
        }
        return calculateDirections(position, hanPalace);
    }

    private List<Direction> calculateDirections(Position position, List<Position> palace) {
        Position centerPosition = palace.get(4);
        if (position.compareRow(centerPosition) ^ position.compareColumn(centerPosition)) {
            return List.of();
        }
        return Direction.diagonalDirections();
    }
}
