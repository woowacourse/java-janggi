package domain.movepolicy;

import java.util.List;
import domain.movepolicy.destination.DestinationRule;
import domain.movepolicy.path.PathRule;
import domain.position.Position;

public record MoveContext(List<Position> pathPositions, DestinationRule destinationRule,
                          PathRule pathRule) {
}
