package domain.unit.rule;

import domain.position.Position;
import domain.position.Route;
import domain.unit.UnitType;
import java.util.List;

public interface UnitRule {

    List<Route> calculateAllRoute(Position start);

    UnitType getType();
}
