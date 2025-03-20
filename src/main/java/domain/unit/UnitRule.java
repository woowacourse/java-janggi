package domain.unit;

import domain.Position;
import domain.Route;
import domain.UnitType;
import java.util.List;

public interface UnitRule {

    List<Route> calculateAllRoute(Position start);

    UnitType getType();
}
