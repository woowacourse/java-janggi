package domain.unit;

import domain.Route;
import domain.UnitType;
import java.util.List;
import java.util.Objects;

public class Unit {
    private final Point point;
    private final Team team;
    private final UnitRule unitRule;


    public Unit(Point point, Team team, UnitRule unitRule) {
        this.point = point;
        this.team = team;
        this.unitRule = unitRule;
    }

    public static Unit of(Point point, Team team, UnitRule unitRule) {
        return new Unit(point, team, unitRule);
    }

    public List<Route> calculateRoutes() {
        return unitRule.calculateAllRoute(this.point);
    }

    public boolean isSamePoint(Point point) {
        return this.point.equals(point);
    }

    public UnitType getType() {
        return this.unitRule.getType();
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Unit carUnit = (Unit) object;
        return Objects.equals(point, carUnit.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(point);
    }
}
