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
    public static final String PICK_OPPOSITE_UNIT_EXCEPTION = "상대팀 말은 고를 수 없습니다.";
    public static final String CANNOT_MOVE_EXCEPTION = "이동할 수 없는 도착지입니다.";

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

    public void doTurn(Position pick, Position destination) {
        if (!canMove(pick, destination)) {
            throw new IllegalArgumentException(CANNOT_MOVE_EXCEPTION);
        }
        Unit pickedUnit = units.get(pick);
        Unit destinationUnit = units.get(destination);
        if (destinationUnit != null && destinationUnit.getTeam() == turn.getOpposite()) {
            units.remove(destination);
        }
        units.remove(pick);
        units.put(destination, pickedUnit);
        switchTurn();
    }

    private boolean canMove(Position pick, Position destination) {
        List<Route> movableRoutes = findMovableRoutesFrom(pick);
        return movableRoutes.stream()
                .map(route -> route.searchDestination(pick))
                .anyMatch(position -> position.equals(destination));
    }

    private void switchTurn() {
        turn = turn.getOpposite();
    }

    public List<Route> findMovableRoutesFrom(Position pick) {
        if (isEmptyPosition(pick)) {
            throw new IllegalArgumentException(EMPTY_POINT_EXCEPTION);
        }
        Unit pickedUnit = units.get(pick);
        if (pickedUnit.getTeam() != turn) {
            throw new IllegalArgumentException(PICK_OPPOSITE_UNIT_EXCEPTION);
        }

        List<Route> totalRoutes = pickedUnit.calculateRoutes(pick);
        totalRoutes = filterRoutesByUnitType(pickedUnit, pick, totalRoutes);
        if (pickedUnit.getType() == UnitType.CANNON) {
            return totalRoutes;
        }
        return filterBlockedRoutes(pick, totalRoutes);
    }

    private List<Route> filterRoutesByUnitType(Unit pickedUnit, Position pick, List<Route> totalRoutes) {
        UnitType type = pickedUnit.getType();
        if (type == UnitType.CANNON) {
            return totalRoutes.stream()
                    .filter(route -> canCannonJump(pick, route))
                    .toList();
        }
        if (type == UnitType.SOLDIER) {
            return filterSoldierMoves(pick, pickedUnit, totalRoutes);
        }
        return totalRoutes;
    }

    private List<Route> filterSoldierMoves(Position pick, Unit pickedUnit, List<Route> totalRoutes) {
        if (pickedUnit.getTeam() == Team.HAN) {
            return totalRoutes.stream()
                    .filter(route -> route.getPositions().getFirst().getY() >= pick.getY())
                    .toList();
        }
        return totalRoutes.stream()
                .filter(route -> route.getPositions().getFirst().getY() <= pick.getY())
                .toList();
    }

    private boolean canCannonJump(Position current, Route route) {
        Position endPoint = route.searchDestination(current);
        if (!isEmptyPosition(endPoint)) {
            Unit endUnit = units.get(endPoint);
            if (endUnit.getType() == UnitType.CANNON) {
                return false;
            }
        }

        int count = 0;
        for (Position position : route.getPositionsExceptDestination(current)) {
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

    private List<Route> filterBlockedRoutes(Position pick, List<Route> routes) {
        return routes.stream()
                .filter(route -> isClearRoute(pick, route))
                .filter(route -> isClearDestination(pick, route))
                .toList();
    }

    private boolean isClearRoute(Position pick, Route route) {
        return route.getPositionsExceptDestination(pick).stream()
                .allMatch(this::isEmptyPosition);
    }

    private boolean isClearDestination(Position pick, Route route) {
        Position endPosition = route.searchDestination(pick);
        if (isEmptyPosition(endPosition)) {
            return true;
        }
        Unit endPointUnit = units.get(endPosition);
        return endPointUnit.getTeam() != this.turn;
    }

    private boolean isEmptyPosition(Position position) {
        return !units.containsKey(position);
    }

    public boolean isOneOfTeamNonExist() {
        long hanUnitCount = units.values().stream().
                filter(unit -> unit.getTeam() == Team.HAN)
                .count();
        long choUnitCount = units.values().stream().
                filter(unit -> unit.getTeam() == Team.CHO)
                .count();
        return (hanUnitCount == 0 || choUnitCount == 0);
    }

    public Team getTurn() {
        return turn;
    }

    public Map<Position, Unit> getUnits() {
        return units;
    }
}
