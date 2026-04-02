package domain.strategy;

import domain.board.BoardBounds;
import domain.coordinate.Path;
import domain.coordinate.Position;
import java.util.List;

public interface Strategy {
    List<Path> getPaths(Position start, BoardBounds bounds);
}
