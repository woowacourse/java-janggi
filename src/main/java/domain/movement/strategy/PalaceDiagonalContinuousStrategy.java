package domain.movement.strategy;

import domain.common.Direction;
import domain.common.Position;
import domain.board.Palace;
import domain.movement.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PalaceDiagonalContinuousStrategy implements MovementStrategy {
    @Override
    public List<Path> generatePaths(Position current) {
        return Palace.diagonals(current).stream()
                .map(direction -> createPath(current, direction))
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<Path> createPath(Position current, Direction direction) {
        List<Position> positions = new ArrayList<>();
        Position cursor = current;

        while (Palace.canMoveDiagonal(cursor, direction)) {
            cursor = cursor.move(direction);
            positions.add(cursor);
        }

        if (positions.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Path(positions));
    }
}
