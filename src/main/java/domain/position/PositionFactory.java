package domain.position;

import java.util.*;

public class PositionFactory {

    private static final int[] BASIC_DX = {1, -1, 0, 0};
    private static final int[] BASIC_DY = {0, 0, 1, -1};

    private static final int[] DIAGONAL_DX = {1, -1, 1, -1};
    private static final int[] DIAGONAL_DY = {1, -1, -1, 1};


    private static final Set<Position> CACHE = new HashSet<>();
    private static final Map<Position, Set<Position>> graph = new HashMap<>();

    static {
        for (int x = 1; x <= 9; x++) {
            for (int y = 1; y <= 10; y++) {
                CACHE.add(new Position(x, y));
            }
        }
    }

    public void basicSettingGraph() {
        settingGraph(BASIC_DX, BASIC_DY);
    }

    public void diagonalSettingGraph(List<Position> diagonalPositions) {
        for (Position diagonalPosition : diagonalPositions) {
            for (int dir = 0; dir < DIAGONAL_DX.length; dir++) {
                int nextX = diagonalPosition.x() + DIAGONAL_DX[dir];
                int nextY = diagonalPosition.y() + DIAGONAL_DY[dir];
                Position neighbor = new Position(nextX, nextY);
                graph.get(diagonalPosition).add(neighbor);
                graph.get(neighbor).add(diagonalPosition);
            }
        }
    }

    private void settingGraph(int[] DX, int[] DY) {
        for (Position pos : CACHE) {
            graph.put(pos, new HashSet<>());
        }

        for (Position pos : CACHE) {
            for (int dir = 0; dir < DX.length; dir++) {
                int nextX = pos.x() + DX[dir];
                int nextY = pos.y() + DY[dir];
                if (isCoordinate(nextX, nextY)) {
                    Position neighbor = new Position(nextX, nextY);
                    graph.get(pos).add(neighbor);
                    graph.get(neighbor).add(pos);
                }
            }
        }
    }

    public static void validateAdjacentPositionBy(Position src, Position dest) {
        Set<Position> adjacentPositions = graph.get(src);
        if (!adjacentPositions.contains(dest)) {
            throw new IllegalArgumentException("dest로 향할 수 있는 node가 존재하지 않습니다.");
        }
    }

    private boolean isCoordinate(int nextX, int nextY) {
        return Position.isCoordinate(nextX, nextY);
    }
}
