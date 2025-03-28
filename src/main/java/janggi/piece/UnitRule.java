package janggi.piece;

import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

public interface UnitRule {
    List<Route> calculateAllRoute(Position start);

    UnitType getType();
}
