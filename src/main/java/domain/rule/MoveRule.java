package domain.rule;

import domain.position.Position;
import java.util.List;

public interface MoveRule {
    boolean canMove(Position source, Position target);

    List<Position> calculateRoute(Position source, Position target);
}
