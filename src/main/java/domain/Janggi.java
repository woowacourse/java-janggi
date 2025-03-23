package domain;

import domain.position.Position;
import domain.position.Route;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Janggi {

    public static final String EMPTY_POINT_EXCEPTION = "해당 위치에 기물이 존재하지 않습니다.";

    private final Map<Position, Unit> units;
    private Team turn;

    private Janggi(Map<Position, Unit> units, Team turn) {
        this.units = new HashMap<>(units);
        this.turn = turn;
    }

    public static Janggi of(Map<Position, Unit> hanUnits, Map<Position, Unit> choUnits, Team turn) {
        Map<Position, Unit> units = new HashMap<>();
        units.putAll(hanUnits);
        units.putAll(choUnits);
        return new Janggi(units, turn);
    }

    public List<Route> findMovableRoutesFrom(Position pick) {
        if (isEmptyPosition(pick)) {
            throw new IllegalArgumentException(EMPTY_POINT_EXCEPTION);
        }

        Unit pickedUnit = units.get(pick);
        List<Route> totalRoutes = pickedUnit.calculateRoutes(pick);
        totalRoutes = filterRoutesByUnitType(pickedUnit, pick, totalRoutes);
        return filterBlockedRoutes(totalRoutes);
    }

    private List<Route> filterRoutesByUnitType(Unit pickedUnit, Position pick, List<Route> totalRoutes) {
        UnitType type = pickedUnit.getType();
        if (type == UnitType.CANNON) {
            return totalRoutes.stream().filter(this::canCannonJump).toList();
        }
        if (type == UnitType.SOLDIER) {
            return filterSoldierMoves(pick, pickedUnit, totalRoutes);
        }
        return totalRoutes;
    }

    private List<Route> filterSoldierMoves(Position pick, Unit pickedUnit, List<Route> totalRoutes) {
        if (pickedUnit.getTeam() == Team.HAN) {
            return totalRoutes.stream()
                    .filter(route -> route.getPoints().getFirst().getY() >= pick.getY())
                    .toList();
        }
        return totalRoutes.stream()
                .filter(route -> route.getPoints().getFirst().getY() <= pick.getY())
                .toList();
    }

    private boolean canCannonJump(Route route) {
        int count = 0;
        for (Position position : route.getPointsExceptEndPoint()) {
            if (isEmptyPosition(position)) {
                continue;
            }
            Unit unit = units.get(position);
            if (unit.getType() == UnitType.CANNON) {
                return false;
            }
            count++;
        }
        return (count == 1);
    }

    private List<Route> filterBlockedRoutes(List<Route> routes) {
        return routes.stream()
                .filter(this::isClearRoute)
                .filter(this::isClearDestination)
                .toList();
    }

    private boolean isClearRoute(Route route) {
        return route.getPointsExceptEndPoint().stream()
                .allMatch(this::isEmptyPosition);
    }

    private boolean isClearDestination(Route route) {
        Position endPosition = route.searchEndPoint();
        if (isEmptyPosition(endPosition)) {
            return true;
        }
        Unit endPointUnit = units.get(endPosition);
        return endPointUnit.getTeam() != this.turn;
    }

    private boolean isEmptyPosition(Position position) {
        return !units.containsKey(position);
    }

    public void switchTurn() {
        turn = turn.getOpposite();
    }

    public Team getTurn() {
        return turn;
    }

    public Map<Position, Unit> getUnits() {
        return units;
    }
}
