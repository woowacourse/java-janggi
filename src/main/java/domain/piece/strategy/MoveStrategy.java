package domain.piece.strategy;

import domain.position.Position;
import java.util.List;

public interface MoveStrategy {
    List<Position> findMovablePath(Position start, Position destination);

}
