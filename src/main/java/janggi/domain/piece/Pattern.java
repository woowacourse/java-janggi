package janggi.domain.piece;

import janggi.domain.board.coordinate.PathStrategy;
import java.util.List;

public record Pattern(
        List<Direction> pattern,
        PathStrategy pathStrategy
) {

}
