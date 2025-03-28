package domain.unit.rule;

import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.List;

public interface UnitRule {

    List<Movement> generatePossibleMovement();

    UnitType getType();
}
