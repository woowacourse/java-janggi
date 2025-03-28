package janggi.piece;

import janggi.position.Position;
import janggi.position.Route;
import java.util.List;

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
}
