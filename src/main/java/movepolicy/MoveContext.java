package movepolicy;

import java.util.List;
import movepolicy.destination.DestinationRule;
import movepolicy.path.PathRule;
import position.Position;

public record MoveContext(List<Position> pathPositions, DestinationRule destinationRule,
                          PathRule pathRule) {
}
