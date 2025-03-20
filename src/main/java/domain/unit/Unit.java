package domain.unit;

import domain.Position;
import domain.Route;
import domain.Team;
import domain.UnitType;
import java.util.List;
import java.util.Objects;

public class Unit {
    private final Position position;
    private final Team team;
    private final UnitRule unitRule;


    public Unit(Position position, Team team, UnitRule unitRule) {
        this.position = position;
        this.team = team;
        this.unitRule = unitRule;
    }

    public static Unit of(Position position, Team team, UnitRule unitRule) {
        return new Unit(position, team, unitRule);
    }

    public List<Route> calculateRoutes() {
        return unitRule.calculateAllRoute(this.position);
    }

    public boolean isSamePoint(Position position) {
        return this.position.equals(position);
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
        return Objects.equals(position, carUnit.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
