package domain.unit;

import domain.position.Position;
import domain.position.Route;
import domain.position.Routes;
import domain.unit.rule.UnitRule;
import java.util.ArrayList;
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

    public Routes calculateRoutes(Position position) {
        List<Route> routes = new ArrayList<>();
        List<Movement> movements = unitRule.generatePossibleMovement();
        for (Movement movement : movements) {
            try {
                Route route = movement.calculateRouteBy(position);
                routes.add(route);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return Routes.of(routes);
    }


    public boolean isSameTeam(Team team) {
        return (this.team == team);
    }

    public boolean isOppositeTeamWith(Unit other) {
        return (this.team != other.team);
    }

    public boolean isSameType(UnitType type) {
        return unitRule.getType() == type;
    }

    public UnitType getType() {
        return this.unitRule.getType();
    }

    public Team getTeam() {
        return team;
    }
}
