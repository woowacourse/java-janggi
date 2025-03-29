package domain.unit.rule;

import static domain.unit.Direction.LEFT;
import static domain.unit.Direction.LOWER;
import static domain.unit.Direction.RIGHT;
import static domain.unit.Direction.UPPER;

import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.List;

public class SoldierUnitRule implements UnitRule {

    @Override
    public List<Movement> generatePossibleMovement() {
        return List.of(
                Movement.of(UPPER),
                Movement.of(LOWER),
                Movement.of(LEFT),
                Movement.of(RIGHT));
    }

    @Override
    public UnitType getType() {
        return UnitType.SOLDIER;
    }
}
