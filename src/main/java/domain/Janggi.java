package domain;

import domain.unit.Point;
import java.util.ArrayList;
import java.util.List;
import domain.unit.Team;
import domain.unit.Unit;
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

    public List<Route> searchAvailableRoutes(Point pick) {
        Unit pickedUnit = findUnitByPoint(pick)
                .orElseThrow(() -> new IllegalArgumentException(""));
        List<Route> totalRoutes = pickedUnit.calculateRoutes();
        return applyUnitProperty(pickedUnit, pick, totalRoutes);
    }

    private List<Route> applyUnitProperty(Unit pickedUnit, Point pick, List<Route> totalRoutes) {
        String type = pickedUnit.getType();
        if (type.equals("포")) {
            totalRoutes = totalRoutes.stream().filter(this::canBombJump).toList();
            return totalRoutes.stream().filter(this::isAvailableEndPoint).toList();
        }
        if (type.equals("졸")) {
            return searchJolRoutes(pick, pickedUnit, totalRoutes);
        }
        return findAvailableRoute(totalRoutes);
    }

    private static List<Route> searchJolRoutes(Point pick, Unit pickedUnit, List<Route> totalRoutes) {
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
        for (Point point : route.getPointsExceptEndPoint()) {
            Optional<Unit> unit = findUnitByPoint(point);
            if (unit.isPresent() && unit.get().getType().equals("포")) {
                return false;
            }
            if (unit.isPresent()) {
                count++;
            }
        }
        return (count == 1);
    }

    private Optional<Unit> findUnitByPoint(Point pick) {
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
        Point endPoint = route.searchEndPoint();
        Optional<Unit> endPointUnit = findUnitByPoint(endPoint);
        return endPointUnit.isEmpty() || endPointUnit.get().getTeam() != this.turn;
    }

    public boolean isEmptyPoint(Point point) {
        return units.stream()
                .noneMatch(unit -> unit.isSamePoint(point));
    }
}
