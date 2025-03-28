package janggi.piece;

import janggi.position.Position;
import janggi.position.Route;
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
