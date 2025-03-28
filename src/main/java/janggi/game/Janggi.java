package janggi.game;

import janggi.position.Position;
import janggi.position.Route;
import janggi.unit.Team;
import janggi.unit.Unit;
import janggi.unit.UnitType;
import java.util.HashMap;
import java.util.List;

public class Janggi {
    private final Units units;
    private Team turn;

    public Janggi(Units units, Team startTurn) {
        this.units = units;
        this.turn = startTurn;
    }

    public void judgeUnitTurn(Position position) {
        Team unitTeam = units.findTeamByPosition(position);
        if (unitTeam != this.turn) {
            throw new IllegalArgumentException("현재 차례가 아닙니다.");
        }
    }

    public void changeTurn() {
        turn = turn.getOppositie();
    }

    public List<Route> searchAvailableRoutes(Position pickedPosition) {
        Unit pickedUnit = units.findUnitByPosition(pickedPosition);
        List<Route> totalRoutes = pickedUnit.calculateRoutes(pickedPosition);
        // todo: 기물 특성은 route에서 적용하도록 수정한다
        List<Route> routes = applyUnitProperty(pickedUnit, pickedPosition, totalRoutes);
        if (routes.isEmpty()) {
            throw new IllegalArgumentException("해당 기물의 이동 가능한 경로가 없습니다.");
        }
        return routes;
    }

    // todo: 기물 특성은 route에서 적용하도록 수정한다
    private List<Route> applyUnitProperty(Unit pickedUnit, Position pick, List<Route> totalRoutes) {
        UnitType type = pickedUnit.getType();
        if (type == UnitType.BOMB) {
            totalRoutes = totalRoutes.stream().filter(route -> route.canBombJump(units)).toList();
            return totalRoutes.stream()
                    .filter(route -> isAvailableEndPoint(route, pick))
                    .toList();
        }
        if (type == UnitType.JOL) {
            // todo: 이 책임은 JOL 자기 자신에게 있음 -> 책임 변경
            return searchJolRoutes(pick, pickedUnit, totalRoutes).stream()
                    .filter(route -> isAvailableEndPoint(route, pick))
                    .toList();
        }
        return findAvailableRoute(totalRoutes, pick);
    }

    // todo: 이 책임은 JOL 자기 자신에게 있음 -> 책임 변경
    private List<Route> searchJolRoutes(Position pick, Unit pickedUnit, List<Route> totalRoutes) {
        if (pickedUnit.getTeam() == Team.HAN) {
            return totalRoutes.stream()
                    .filter(route -> route.getPoints().getFirst().getY() >= pick.getY())
                    .toList();
        }
        return totalRoutes.stream()
                .filter(route -> route.getPoints().getFirst().getY() <= pick.getY())
                .toList();
    }

    public boolean isNoneEnemyUnit() {
        return units.isNoneSameTeamUnit(turn);
    }

    private List<Route> findAvailableRoute(List<Route> routes, Position startPoint) {
        return routes.stream()
                .filter(this::isAvailablePath)
                .filter(route -> isAvailableEndPoint(route, startPoint))
                .toList();
    }

    public boolean isAvailablePath(Route route) {
        return route.getPointsExceptEndPoint().stream()
                .allMatch(units::isEmptyPoint);
    }

    private boolean isAvailableEndPoint(Route route, Position startPoint) {
        Position endPosition = route.searchEndPoint(startPoint);
        if (units.isExistUnit(endPosition)) {
            return turn != units.findTeamByPosition(endPosition);
        }
        return true;
    }

    public void moveAndCaptureIfEnemyExists(Route route, Position startPoint) {
        Position endPoint = route.searchEndPoint(startPoint);
        units.moveAndCaptureIfEnemyExists(startPoint, endPoint);
    }

    public Team getTurn() {
        return turn;
    }

    public HashMap<Position, Unit> getUnits() {
        return units.getUnits();
    }
}
