package position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DirectionSequence {
    private final List<Direction> directions;

    public DirectionSequence(List<Direction> directions) {
        validateDirections(directions);
        this.directions = List.copyOf(directions);
    }

    public static DirectionSequence of(Direction... directions) {
        return new DirectionSequence(Arrays.asList(directions));
    }

    public DirectionSequenceResult positionsFrom(Position departure) {
        List<Position> positions = new ArrayList<>();
        Position current = departure;

        for (Direction direction : directions) {
            current = direction.move(current);
            positions.add(current);
        }
        return new DirectionSequenceResult(positions);
    }

    private void validateDirections(List<Direction> directions) {
        if (directions == null) {
            throw new IllegalArgumentException("방향 조합은 null일 수 없습니다.");
        }
        if (directions.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("방향 조합에는 null 방향이 포함될 수 없습니다.");
        }
    }
}
