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

    // 가능한 모든 경로 반환
    public List<Route> searchAvailableRoutes(Point pick) {
        Unit pickedUnit = findUnitByPoint(pick)
                .orElseThrow(() -> new IllegalArgumentException(""));
        // 기물 상관없는 모든 경로
        List<Route> totalRoutes = pickedUnit.calculateRoutes();

        String type = pickedUnit.getType();
        if (type.equals("포")) {
            // 경로 중에 포가 있는지 확인 & 다른 기물이 1개인지 확인
            totalRoutes = totalRoutes.stream().filter(this::canBombJump).toList();
            return totalRoutes.stream().filter(this::isAvailableEndPoint).toList();
        }
        //
        totalRoutes = findAvailableRoute(totalRoutes);
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
        return totalRoutes;
    }

    private boolean canBombJump(Route route) {
        int count = 0;
        for (Point point : route.getPointsExceptEndPoint()) {
            Optional<Unit> unit = findUnitByPoint(point);
            if (unit.isEmpty()) {
                continue;
            }
            if (unit.get().getType().equals("포")) {
                return false;
            }
            count++;
        }
        return (count == 1);
    }

    private Optional<Unit> findUnitByPoint(Point pick) {
        return units.stream()
                .filter(unit -> unit.isSamePoint(pick))
                .findFirst();
    }

    // 유효한 경로를 반환
    private List<Route> findAvailableRoute(List<Route> routes) {
        return routes.stream()
                .filter(this::isAvailableRoute)
                .filter(this::isAvailableEndPoint)
                .toList();
    }

    // 경로에 기물이 없어야 함
    public boolean isAvailableRoute(Route route) {
        return route.getPointsExceptEndPoint().stream()
                .allMatch(this::isEmptyPoint);
    }

    // 엔드포인트에 기물이 없거나, 기물이 있다면 상대편 기물이어야 함
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
