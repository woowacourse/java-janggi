package janggi.domain.movement;

import java.util.Arrays;
import java.util.List;

public record Directions(List<Direction> directions) {

    public static Directions of(Direction... directions) {
        return new Directions(Arrays.asList(directions));
    }

    public static List<Directions> rotate(List<Directions> directionsList) {
        return directionsList.stream()
                .map(Directions::directions)
                .map(directions -> directions.stream()
                        .map(Direction::rotate)
                        .toList()
                )
                .map(Directions::new)
                .toList();
    }
}
