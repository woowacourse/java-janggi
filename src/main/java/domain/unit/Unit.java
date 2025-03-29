package domain.unit;

import domain.movement.Movement;
import domain.position.Position;
import domain.position.Route;
import domain.position.Routes;
import domain.unit.move.MovingStrategy;
import java.util.ArrayList;
import java.util.List;

public class Unit {

    private final Team team;
    private final MovingStrategy movingStrategy;
    private final UnitType type;

    public Unit(Team team, MovingStrategy movingStrategy, UnitType type) {
        this.team = team;
        this.movingStrategy = movingStrategy;
        this.type = type;
    }

    public static Unit of(Team team, MovingStrategy movingStrategy, UnitType type) {
        return new Unit(team, movingStrategy, type);
    }

    public Routes calculateRoutes(Position position) {
        List<Route> routes = new ArrayList<>();
        List<Movement> movements = movingStrategy.generatePossibleMovement(position);
        if (type.canNotMoveOutOfPalace()) {
            movements = movements.stream()
                    .filter(movement -> movement.calculateRouteBy(position).searchDestination(position).isPalace())
                    .toList();
        }
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
        return this.type == type;
    }

    public int getScore() {
        return type.getScore();
    }

    public UnitType getType() {
        return this.type;
    }

    public Team getTeam() {
        return team;
    }
}
