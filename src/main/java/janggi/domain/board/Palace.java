package janggi.domain.board;


import janggi.domain.common.Direction;
import janggi.domain.common.Position;
import janggi.domain.route.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum Palace {

    CHO(8, 10, Map.of(
            new Position(4, 8), List.of(new Route(List.of(Direction.DOWN_RIGHT))),
            new Position(6, 8), List.of(new Route(List.of(Direction.DOWN_LEFT))),
            new Position(5, 9), List.of(
                    new Route(List.of(Direction.UP_LEFT)),
                    new Route(List.of(Direction.UP_RIGHT)),
                    new Route(List.of(Direction.DOWN_LEFT)),
                    new Route(List.of(Direction.DOWN_RIGHT))),
            new Position(4, 10), List.of(new Route(List.of(Direction.UP_RIGHT))),
            new Position(6, 10), List.of(new Route(List.of(Direction.UP_LEFT))))),

    HAN(1, 3, Map.of(
            new Position(4, 1), List.of(new Route(List.of(Direction.DOWN_RIGHT))),
            new Position(6, 1), List.of(new Route(List.of(Direction.DOWN_LEFT))),
            new Position(5, 2), List.of(
                    new Route(List.of(Direction.UP_LEFT)),
                    new Route(List.of(Direction.UP_RIGHT)),
                    new Route(List.of(Direction.DOWN_LEFT)),
                    new Route(List.of(Direction.DOWN_RIGHT))),
            new Position(4, 3), List.of(new Route(List.of(Direction.UP_RIGHT))),
            new Position(6, 3), List.of(new Route(List.of(Direction.UP_LEFT)))));

    private final int START_X = 4;
    private final int END_X = 6;

    private final List<Position> palacePositions;
    private final Map<Position, List<Route>> diagonalInfos;

    Palace(int startY, int endY, Map<Position, List<Route>> diagonalInfos) {
        this.palacePositions = initializePalacePositions(startY, endY);
        this.diagonalInfos = diagonalInfos;
    }

    public boolean isInPalace(Position position) {
        return palacePositions.contains(position);
    }

    private List<Position> initializePalacePositions(int startY, int endY) {
        List<Position> palacePositions = new ArrayList<>();
        for (int x = START_X; x <= END_X; x++) {
            makePalacePositionsByYRange(startY, endY, palacePositions, x);
        }
        return palacePositions;
    }

    private void makePalacePositionsByYRange(int startY, int endY, List<Position> palacePositions, int x) {
        for (int y = startY; y <= endY; y++) {
            palacePositions.add(new Position(x, y));
        }
    }

    public List<Route> findDiagonalRoutes(Position position) {
        return diagonalInfos.getOrDefault(position, List.of());
    }
}
