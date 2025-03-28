package domain.unit.rule;

import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.List;

public class NoneUnitRule implements UnitRule {

    @Override
    public List<Movement> generatePossibleMovement() {
        return List.of();
    }

    @Override
    public UnitType getType() {
        return UnitType.NONE;
    }
}
