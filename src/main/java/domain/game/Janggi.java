package domain.game;

import domain.position.Position;
import domain.position.Route;
import domain.unit.DefaultUnitPosition;
import domain.unit.Team;
import domain.unit.Unit;
import domain.unit.UnitType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Janggi {
    private final List<Unit> units = new ArrayList<>();
    private Team turn;

    public Janggi() {
        turn = Team.CHO;
        List<Unit> hanUnits = settingUnits(Team.HAN);
        List<Unit> choUnits = settingUnits(Team.CHO);
        this.units.addAll(Stream.concat(hanUnits.stream(), choUnits.stream()).toList());
    }

    private List<Unit> settingUnits(Team team) {
        List<Unit> units = new ArrayList<>();
        for (DefaultUnitPosition value : DefaultUnitPosition.values()) {
            units.addAll(DefaultUnitPosition.createDefaultUnits(value, team));
        }
        return units;
    }

    public void judgeUnitTurn(Position position) {
        Optional<Unit> unit = findUnitByPoint(position);
        if (unit.isEmpty() || unit.get().getTeam() != this.turn) {
            throw new IllegalArgumentException("기물이 없거나 현재 차례가 아닙니다.");
        }
    }

    public List<Route> searchAvailableRoutes(Position pick) {
        Unit pickedUnit = findUnitByPoint(pick)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
        List<Route> totalRoutes = pickedUnit.calculateRoutes();
        return applyUnitProperty(pickedUnit, pick, totalRoutes);
    }

    private List<Route> applyUnitProperty(Unit pickedUnit, Position pick, List<Route> totalRoutes) {
        UnitType type = pickedUnit.getType();
        if (type == UnitType.BOMB) {
            totalRoutes = totalRoutes.stream().filter(this::canBombJump).toList();
            return totalRoutes.stream().filter(this::isAvailableEndPoint).toList();
        }
        if (type == UnitType.JOL) {
            return searchJolRoutes(pick, pickedUnit, totalRoutes);
        }
        return findAvailableRoute(totalRoutes);
    }

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

    private boolean canBombJump(Route route) {
        int count = 0;
        for (Position position : route.getPointsExceptEndPoint()) {
            Optional<Unit> unit = findUnitByPoint(position);
            if (unit.isPresent() && unit.get().getType() == UnitType.BOMB) {
                return false;
            }
            if (unit.isPresent()) {
                count++;
            }
        }
        return (count == 1);
    }

    private Optional<Unit> findUnitByPoint(Position pick) {
        return units.stream()
                .filter(unit -> unit.isSamePoint(pick))
                .findFirst();
    }

    private List<Route> findAvailableRoute(List<Route> routes) {
        return routes.stream()
                .filter(this::isAvailableRoute)
                .filter(this::isAvailableEndPoint)
                .toList();
    }

    public boolean isAvailableRoute(Route route) {
        return route.getPointsExceptEndPoint().stream()
                .allMatch(this::isEmptyPoint);
    }

    public boolean isAvailableEndPoint(Route route) {
        Position endPosition = route.searchEndPoint();
        Optional<Unit> endPointUnit = findUnitByPoint(endPosition);
        return endPointUnit.isEmpty() || endPointUnit.get().getTeam() != this.turn;
    }

    public boolean isEmptyPoint(Position position) {
        return units.stream()
                .noneMatch(unit -> unit.isSamePoint(position));
    }

    public void changeTurn() {
        if (turn == Team.HAN) {
            turn = Team.CHO;
            return;
        }
        turn = Team.HAN;
    }

    public Team getTurn() {
        return turn;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void moveAndCaptureIfEnemyExists(Route route) {

    }
}
