package domain.unit;

import domain.Route;
import domain.UnitType;
import java.util.List;

public interface UnitRule {

    List<Route> calculateAllRoute(Point start);

    UnitType getType();
}
