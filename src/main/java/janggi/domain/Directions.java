package janggi.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Directions that = (Directions) o;
        return Objects.equals(directions, that.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(directions);
    }
}
