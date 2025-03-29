package domain.unit.rule;

import domain.position.Position;
import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.List;

public interface MovingStrategy {

    List<Movement> generatePossibleMovement(Position position);

    UnitType getType();
}
