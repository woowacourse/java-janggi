package domain.unit.rule;

import domain.position.Position;
import domain.position.Route;
import domain.unit.UnitType;
import java.util.List;

public class NoneUnitRule implements UnitRule {

    @Override
    public List<Route> calculateAllRoute(Position start) {
        return List.of();
    }

    @Override
    public UnitType getType() {
        return UnitType.NONE;
    }
}
