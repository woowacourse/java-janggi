package domain.unit;

import domain.Route;
import java.util.List;

public interface UnitRule {

    List<Route> calculateAllRoute(Point start);

    String getType();
}
