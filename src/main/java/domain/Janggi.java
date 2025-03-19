package domain;

import domain.unit.Point;
import java.util.ArrayList;
import java.util.List;
import domain.unit.Team;
import domain.unit.Unit;
import java.util.stream.Stream;

public class Janggi {
    private final List<Unit> units = new ArrayList<>();
    private Team turn;

    public Janggi() {
        turn = Team.CHO;
        // han
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
        Unit pickedUnit = findUnitByPoint(pick);
        List<Route> totalRoutes = pickedUnit.calculateRoutes();
        totalRoutes = removeUnavailableRoute(totalRoutes);

        String type = pickedUnit.getType();
        // todo: type으로 계산
        if (type.equals("졸")) {
            if (pickedUnit.getTeam() == Team.HAN) {
                return totalRoutes.stream()
                        .filter(route -> route.getPoints().getFirst().getY() >= pick.getY())
                        .toList();
            }
            return totalRoutes.stream()
                    .filter(route -> route.getPoints().getFirst().getY() <= pick.getY())
                    .toList();
        }
        if (type.equals("포")) {
            // TODO: 포 구현하기
        }
        return totalRoutes;
    }

    private Unit findUnitByPoint(Point pick) {
        return units.stream()
                .filter(unit -> unit.isSamePoint(pick))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(""));
    }

    private List<Route> removeUnavailableRoute(List<Route> routes) {
        for (Route route : routes) {
            List<Point> points = route.getPoints();
            for (int i = 0; i < points.size(); i++) {
                if (i != (points.size() - 1) && !isEmptyPoint(points.get(i))) {
                    routes.remove(route);
                    break;
                }
                if (i == points.size() - 1) {
                    Point endPoint = points.get(i);
                    Unit endUnit = findUnitByPoint(endPoint);
                    if (endUnit.getTeam() == this.turn) {
                        routes.remove(route);
                        break;
                    }
                }
            }
        }
        return new ArrayList<>(routes);
    }

    public boolean isEmptyPoint(Point point) {
        return units.stream()
                .noneMatch(unit -> unit.isSamePoint(point));
    }
}
