package domain;

import domain.position.Position;
import domain.position.Route;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import domain.unit.Units;
import java.util.List;
import java.util.Map;

public class Janggi {

    public static final String EMPTY_POINT_EXCEPTION = "해당 위치에 기물이 존재하지 않습니다.";
    public static final String PICK_OPPOSITE_UNIT_EXCEPTION = "상대팀 말은 고를 수 없습니다.";
    public static final String CANNOT_MOVE_EXCEPTION = "이동할 수 없는 도착지입니다.";

    private final Units totalUnits;
    private Team turn;

    private Janggi(Units totalUnits, Team turn) {
        this.totalUnits = totalUnits;
        this.turn = turn;
    }

    public static Janggi of(Units totalUnits, Team turn) {
        return new Janggi(totalUnits, turn);
    }

    public void doTurn(Position pick, Position destination) {
        if (!canMove(pick, destination)) {
            throw new IllegalArgumentException(CANNOT_MOVE_EXCEPTION);
        }
        if (totalUnits.isOppositeTeam(pick, destination)) {
            totalUnits.removeUnitAt(destination);
        }
        totalUnits.moveUnit(pick, destination);
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
        if (totalUnits.isEmptyPosition(pick)) {
            throw new IllegalArgumentException(EMPTY_POINT_EXCEPTION);
        }
        if (totalUnits.isUnitTeamNotEqualAt(pick, turn)) {
            throw new IllegalArgumentException(PICK_OPPOSITE_UNIT_EXCEPTION);
        }

        List<Route> unitRoutes = calculateRoutesByUnitType(pick);
        if (totalUnits.isUnitSameType(pick, UnitType.CANNON)) {
            return unitRoutes;
        }
        return filterBlockedRoutes(pick, unitRoutes);
    }

    private List<Route> calculateRoutesByUnitType(Position pick) {
        List<Route> routes = totalUnits.getUnitRoutes(pick);
        if (totalUnits.isUnitSameType(pick, UnitType.CANNON)) {
            return routes.stream()
                    .filter(route -> canCannonJump(pick, route))
                    .toList();
        }
        if (totalUnits.isUnitSameType(pick, UnitType.SOLDIER)) {
            return filterSoldierMoves(pick, routes);
        }
        return routes;
    }

    private List<Route> filterSoldierMoves(Position pick, List<Route> totalRoutes) {
        if (totalUnits.isUnitTeamEqualAt(pick, Team.HAN)) {
            return totalRoutes.stream()
                    .filter(route -> route.getPositions().getFirst().getY() >= pick.getY())
                    .toList();
        }
        return totalRoutes.stream()
                .filter(route -> route.getPositions().getFirst().getY() <= pick.getY())
                .toList();
    }

    private boolean canCannonJump(Position current, Route route) {
        Position destination = route.searchDestination(current);

        if (totalUnits.isNotEmptyPosition(destination) &&
                totalUnits.isUnitSameType(destination, UnitType.CANNON)) {
            return false;
        }

        List<Position> pathPositions = route.getPositionsExceptDestination(current);
        return !hasCannonInPath(pathPositions) && onlyOneUnitInPath(pathPositions);
    }

    private boolean hasCannonInPath(List<Position> positions) {
        return positions.stream()
                .filter(totalUnits::isNotEmptyPosition)
                .anyMatch(position -> totalUnits.isUnitSameType(position, UnitType.CANNON));
    }

    private boolean onlyOneUnitInPath(List<Position> positions) {
        return positions.stream()
                .filter(totalUnits::isNotEmptyPosition)
                .count() == 1;
    }

    private List<Route> filterBlockedRoutes(Position pick, List<Route> routes) {
        return routes.stream()
                .filter(route -> isClearRoute(pick, route))
                .filter(route -> isClearDestination(pick, route))
                .toList();
    }

    private boolean isClearRoute(Position pick, Route route) {
        return route.getPositionsExceptDestination(pick).stream()
                .allMatch(totalUnits::isEmptyPosition);
    }

    private boolean isClearDestination(Position pick, Route route) {
        Position endPosition = route.searchDestination(pick);
        if (totalUnits.isEmptyPosition(endPosition)) {
            return true;
        }
        return totalUnits.isUnitTeamNotEqualAt(endPosition, turn);
    }

    public boolean isOneOfTeamNonExist() { // TODO: 궁, 사 구현 완료 시 게임 종료 조건 변경
        return totalUnits.isTeamNonExist(Team.HAN) || totalUnits.isTeamNonExist(Team.CHO);
    }

    public Team getTurn() {
        return turn;
    }

    public Map<Position, Unit> getUnits() {
        return totalUnits.getAllUnits();
    }
}
