package domain.strategy;

import domain.vo.Position;
import domain.path.Path;
import java.util.List;

public interface PieceMoveStrategy {

    List<Position> findRoute(List<Path> paths, Position source, Position target);

}
