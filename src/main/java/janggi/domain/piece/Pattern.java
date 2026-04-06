package janggi.domain.piece;

import java.util.List;

public record Pattern(List<Direction> directions) {

    public boolean isDiagonal() {
        return directions.stream().anyMatch(Direction::isDiagonal);
    }
}
