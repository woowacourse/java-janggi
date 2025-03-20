package domain.unit;

import domain.Route;
import domain.UnitType;
import java.util.List;

public class NoneUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Point start) {
        return List.of();
    }

    @Override
    public UnitType getType() {
        return UnitType.NONE;
    }
}
