package domain.unit;

import domain.Route;
import java.util.List;

public class ScholarUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Point start) {
        return List.of();
    }

    @Override
    public String getType() {
        return "사";
    }
}
