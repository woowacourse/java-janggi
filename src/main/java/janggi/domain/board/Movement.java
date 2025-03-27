package janggi.domain.board;

import java.util.Arrays;
import java.util.List;

public record Movement(int movedX, int movedY, List<Direction> directions) {
    public static Movement from(Direction... directions) {
        int movedX = Arrays.stream(directions)
                .mapToInt(Direction::getX)
                .sum();
        int movedY = Arrays.stream(directions)
                .mapToInt(Direction::getY)
                .sum();
        return new Movement(movedX, movedY, List.of(directions));
    }
}
