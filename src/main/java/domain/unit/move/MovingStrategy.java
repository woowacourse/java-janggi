package domain.unit.move;

import domain.movement.Movement;
import domain.position.Position;
import java.util.List;

public interface MovingStrategy {

    List<Movement> generatePossibleMovement(Position position);
}
