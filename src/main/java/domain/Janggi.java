package domain;

import domain.unit.Point;
import java.util.ArrayList;
import java.util.List;
import domain.unit.BombUnitRule;
import domain.unit.CarUnitRule;
import domain.unit.ElephantUnitRule;
import domain.unit.HorseUnitRule;
import domain.unit.JolUnitRule;
import domain.unit.KingUnitRule;
import domain.unit.ScholarUnitRule;
import domain.unit.Team;
import domain.unit.Unit;

public class Janggi {
    private final List<Unit> units = new ArrayList<>();

    public Janggi() {
        // han
        units.add(Unit.of(new Point(0, 0), Team.HAN, new CarUnitRule()));
        units.add(Unit.of(new Point(8, 0), Team.HAN, new CarUnitRule()));

        units.add(Unit.of(new Point(2, 0), Team.HAN, new HorseUnitRule()));
        units.add(Unit.of(new Point(7, 0), Team.HAN, new HorseUnitRule()));

        units.add(Unit.of(new Point(1, 0), Team.HAN, new ElephantUnitRule()));
        units.add(Unit.of(new Point(6, 0), Team.HAN, new ElephantUnitRule()));

        units.add(Unit.of(new Point(3, 0), Team.HAN, new ScholarUnitRule()));
        units.add(Unit.of(new Point(5, 0), Team.HAN, new ScholarUnitRule()));

        units.add(Unit.of(new Point(1, 2), Team.HAN, new BombUnitRule()));
        units.add(Unit.of(new Point(7, 2), Team.HAN, new BombUnitRule()));

        units.add(Unit.of(new Point(4, 1), Team.HAN, new KingUnitRule()));

        units.add(Unit.of(new Point(0, 3), Team.HAN, new JolUnitRule()));
        units.add(Unit.of(new Point(2, 3), Team.HAN, new JolUnitRule()));
        units.add(Unit.of(new Point(4, 3), Team.HAN, new JolUnitRule()));
        units.add(Unit.of(new Point(6, 3), Team.HAN, new JolUnitRule()));
        units.add(Unit.of(new Point(8, 3), Team.HAN, new JolUnitRule()));
    }

    public List<Route> searchAvailableRoutes(Point pick) {
        Unit pickedUnit = findUnitByPoint(pick);
        List<Route> totalRoutes = pickedUnit.calculateRoutes();
        return removeUnavailableRoute(totalRoutes);
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
                    break ;
                }
                if (i == points.size() - 1) {
                    // TODO: 아군인지 적군인지 확인하기
                }
            }
        }
        return new ArrayList<>(routes);
    }

    public boolean isEmptyPoint(Point point) {
        return !units.stream()
                .anyMatch(unit -> unit.isSamePoint(point));
    }
}
