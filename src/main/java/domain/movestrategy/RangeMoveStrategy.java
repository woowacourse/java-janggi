package domain.movestrategy;

import domain.Position;
import java.util.List;

public interface RangeMoveStrategy {
    List<Position> calculatePath(Position startPosition, Position targetPosition);
}
