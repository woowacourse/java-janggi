package domain.movestrategy;

import domain.Position;
import domain.Team;
import java.util.List;

public interface FixedMoveStrategy {
    List<Position> calculatePath(Position startPosition, Position targetPosition, Team team);
}
