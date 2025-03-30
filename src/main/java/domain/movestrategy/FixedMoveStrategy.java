package domain.movestrategy;

import domain.Position;
import domain.player.Player;
import java.util.List;

public interface FixedMoveStrategy {
    List<Position> calculatePath(Position startPosition, Position targetPosition, Player player);
}
