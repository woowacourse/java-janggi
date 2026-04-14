package domain.coordinate;

import java.util.List;

public record DirectionSequence(List<Direction> directions) {

    public static DirectionSequence of(Direction... directions) {
        return new DirectionSequence(List.of(directions));
    }
}
