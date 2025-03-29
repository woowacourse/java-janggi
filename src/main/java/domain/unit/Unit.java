package domain.unit;

import domain.position.Position;
import domain.position.Route;
import domain.position.Routes;
import domain.unit.rule.MovingStrategy;
import java.util.ArrayList;
import java.util.List;

public class Unit {

    private final Team team;
    private final MovingStrategy movingStrategy;

    public Unit(Team team, MovingStrategy movingStrategy) {
        this.team = team;
        this.movingStrategy = movingStrategy;
    }

    public static Unit of(Team team, MovingStrategy movingStrategy) {
        return new Unit(team, movingStrategy);
    }

    public Routes calculateRoutes(Position position) {
        List<Route> routes = new ArrayList<>();
        List<Movement> movements = movingStrategy.generatePossibleMovement(position);
        for (Movement movement : movements) {
            routes.add(movement.calculateRouteBy(position));
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
        return movingStrategy.getType() == type;
    }

    public UnitType getType() {
        return this.movingStrategy.getType();
    }

    public Team getTeam() {
        return team;
    }
}
