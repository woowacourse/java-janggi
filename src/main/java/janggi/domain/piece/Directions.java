package janggi.domain.piece;

import janggi.domain.board.coordinate.PathStrategy;
import java.util.List;

public record Directions(
        List<Direction> value,
        PathStrategy pathStrategy
) {

}
