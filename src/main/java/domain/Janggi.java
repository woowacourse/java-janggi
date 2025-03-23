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

    public List<Route> searchAvailableRoutes(Position pick) {
        if (isEmptyPoint(pick)) {
            throw new IllegalArgumentException(EMPTY_POINT_EXCEPTION);
        }
        Unit pickedUnit = units.get(pick);
        List<Route> totalRoutes = pickedUnit.calculateRoutes(pick);
        return applyUnitProperty(pickedUnit, pick, totalRoutes);
    }

    private List<Route> applyUnitProperty(Unit pickedUnit, Position pick, List<Route> totalRoutes) {
        UnitType type = pickedUnit.getType();
        if (type == UnitType.CANNON) {
            totalRoutes = totalRoutes.stream().filter(this::canCannonJump).toList();
            return totalRoutes.stream().filter(this::isAvailableEndPoint).toList();
        }
        if (type == UnitType.SOLDIER) {
            return searchSoldierRoutes(pick, pickedUnit, totalRoutes);
        }
        return findAvailableRoute(totalRoutes);
    }

    private List<Route> searchSoldierRoutes(Position pick, Unit pickedUnit, List<Route> totalRoutes) {
        if (pickedUnit.getTeam() == Team.HAN) {
            return totalRoutes.stream()
                    .filter(route -> route.getPoints().getFirst().getY() >= pick.getY())
                    .filter(this::isAvailableEndPoint)
                    .toList();
        }
        return totalRoutes.stream()
                .filter(route -> route.getPoints().getFirst().getY() <= pick.getY())
                .filter(this::isAvailableEndPoint)
                .toList();
    }

    private boolean canCannonJump(Route route) {
        int count = 0;
        for (Position position : route.getPointsExceptEndPoint()) {
            if (isEmptyPoint(position)) {
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

    private List<Route> findAvailableRoute(List<Route> routes) {
        return routes.stream()
                .filter(this::isAvailableRoute)
                .filter(this::isAvailableEndPoint)
                .toList();
    }

    private boolean isAvailableRoute(Route route) {
        return route.getPointsExceptEndPoint().stream()
                .allMatch(this::isEmptyPoint);
    }

    private boolean isAvailableEndPoint(Route route) {
        Position endPosition = route.searchEndPoint();
        if (isEmptyPoint(endPosition)) {
            return true;
        }
        Unit endPointUnit = units.get(endPosition);
        return endPointUnit.getTeam() != this.turn;
    }

    private boolean isEmptyPoint(Position position) {
        return !units.containsKey(position);
    }

    public void changeTurn() {
        turn = turn.getOpposite();
    }

    public Team getTurn() {
        return turn;
    }

    public Map<Position, Unit> getUnits() {
        return units;
    }
}
