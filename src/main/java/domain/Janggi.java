package domain;

import domain.unit.Point;
import java.util.ArrayList;
import java.util.List;
import domain.unit.BombUnitRule;
import domain.unit.CarUnitRule;
import domain.unit.ElephantUnitRule;
import domain.unit.HorseUnitRule;
import domain.unit.JolUnitRule;
import domain.unit.NoneUnitRule;
import domain.unit.Team;
import domain.unit.Unit;

public class Janggi {
    private final List<Unit> units = new ArrayList<>();
    private Team turn;

    public Janggi() {
        turn = Team.CHO;
        // han
        units.add(Unit.of(new Point(0, 0), Team.HAN, new CarUnitRule()));
        units.add(Unit.of(new Point(8, 0), Team.HAN, new CarUnitRule()));

        units.add(Unit.of(new Point(2, 0), Team.HAN, new HorseUnitRule()));
        units.add(Unit.of(new Point(7, 0), Team.HAN, new HorseUnitRule()));

        units.add(Unit.of(new Point(1, 0), Team.HAN, new ElephantUnitRule()));
        units.add(Unit.of(new Point(6, 0), Team.HAN, new ElephantUnitRule()));

        units.add(Unit.of(new Point(3, 0), Team.HAN, new NoneUnitRule())); // TODO: Scholar 로 교체
        units.add(Unit.of(new Point(5, 0), Team.HAN, new NoneUnitRule())); // TODO: Scholar 로 교체

        units.add(Unit.of(new Point(1, 2), Team.HAN, new BombUnitRule()));
        units.add(Unit.of(new Point(7, 2), Team.HAN, new BombUnitRule()));

        units.add(Unit.of(new Point(4, 1), Team.HAN, new NoneUnitRule())); // TODO: King 로 교체

        units.add(Unit.of(new Point(0, 3), Team.HAN, new JolUnitRule()));
        units.add(Unit.of(new Point(2, 3), Team.HAN, new JolUnitRule()));
        units.add(Unit.of(new Point(4, 3), Team.HAN, new JolUnitRule()));
        units.add(Unit.of(new Point(6, 3), Team.HAN, new JolUnitRule()));
        units.add(Unit.of(new Point(8, 3), Team.HAN, new JolUnitRule()));
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
