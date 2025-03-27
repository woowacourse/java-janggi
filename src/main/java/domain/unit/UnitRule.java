package domain.unit;

import domain.position.Position;
import domain.position.Route;
import java.util.List;

public interface UnitRule {
    List<Route> calculateAllRoute(Position start);

    UnitType getType();
}
