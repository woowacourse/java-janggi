package janggi.unit;

import janggi.position.Position;
import janggi.position.Route;
import java.util.List;
import java.util.Objects;

public class Unit {
    private final Team team;
    private final UnitRule unitRule;

    public Unit(Team team, UnitRule unitRule) {
        this.team = team;
        this.unitRule = unitRule;
    }

    public static Unit of(Team team, UnitRule unitRule) {
        return new Unit(team, unitRule);
    }

    public List<Route> calculateRoutes(Position position) {
        return unitRule.calculateAllRoute(position);
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
        Unit unit = (Unit) object;
        return team == unit.team && Objects.equals(unitRule, unit.unitRule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, unitRule);
    }
}
