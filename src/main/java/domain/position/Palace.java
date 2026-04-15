package domain.position;

import domain.game.Team;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Palace {
    private static final Palace CHO_PALACE = new Palace(1, 3, 4, 6);
    private static final Palace HAN_PALACE = new Palace(8, 10, 4, 6);
    private static final List<Palace> ALL = List.of(CHO_PALACE, HAN_PALACE);

    private final Set<Position> positions;
    private final Map<Position, Set<Position>> diagonalNeighbors;
    private final Map<Position, Position> cornerToCorner;
    private final Position center;

    private Palace(int minRow, int maxRow, int minColumn, int maxColumn) {
        this.positions = buildPositions(minRow, maxRow, minColumn, maxColumn);
        this.center = new Position(midValue(minRow, maxRow), midValue(minColumn, maxColumn));
        this.diagonalNeighbors = buildDiagonalNeighbors(minRow, maxRow, minColumn, maxColumn);
        this.cornerToCorner = buildCornerToCorner(minRow, maxRow, minColumn, maxColumn);
    }

    private int midValue(int min, int max) {
        return (min + max) / 2;
    }

    private Set<Position> buildPositions(int minRow, int maxRow, int minColumn, int maxColumn) {
        Set<Position> result = new HashSet<>();
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minColumn; col <= maxColumn; col++) {
                result.add(new Position(row, col));
            }
        }
        return result;
    }

    private Map<Position, Set<Position>> buildDiagonalNeighbors(
            int minRow, int maxRow, int minColumn, int maxColumn) {
        Position topLeft = new Position(minRow, minColumn);
        Position topRight = new Position(minRow, maxColumn);
        Position bottomLeft = new Position(maxRow, minColumn);
        Position bottomRight = new Position(maxRow, maxColumn);
        Position mid = new Position(midValue(minRow, maxRow), midValue(minColumn, maxColumn));

        Map<Position, Set<Position>> neighbors = new HashMap<>();
        addBidirectional(neighbors, topLeft, mid);
        addBidirectional(neighbors, topRight, mid);
        addBidirectional(neighbors, bottomLeft, mid);
        addBidirectional(neighbors, bottomRight, mid);
        return neighbors;
    }

    private void addBidirectional(Map<Position, Set<Position>> map, Position a, Position b) {
        map.computeIfAbsent(a, k -> new HashSet<>()).add(b);
        map.computeIfAbsent(b, k -> new HashSet<>()).add(a);
    }

    private Map<Position, Position> buildCornerToCorner(
            int minRow, int maxRow, int minColumn, int maxColumn) {
        Position topLeft = new Position(minRow, minColumn);
        Position topRight = new Position(minRow, maxColumn);
        Position bottomLeft = new Position(maxRow, minColumn);
        Position bottomRight = new Position(maxRow, maxColumn);

        Map<Position, Position> pairs = new HashMap<>();
        pairs.put(topLeft, bottomRight);
        pairs.put(bottomRight, topLeft);
        pairs.put(topRight, bottomLeft);
        pairs.put(bottomLeft, topRight);
        return pairs;
    }

    public boolean contains(Position position) {
        return positions.contains(position);
    }

    public boolean isDiagonalAdjacent(Position source, Position target) {
        Set<Position> neighbors = diagonalNeighbors.get(source);
        return neighbors != null && neighbors.contains(target);
    }

    public boolean isDiagonalStraight(Position source, Position target) {
        return isDiagonalAdjacent(source, target) || isCornerToCorner(source, target);
    }

    private boolean isCornerToCorner(Position source, Position target) {
        return target.equals(cornerToCorner.get(source));
    }

    public List<Position> diagonalRoute(Position source, Position target) {
        if (isCornerToCorner(source, target)) {
            return List.of(center);
        }
        return List.of();
    }

    public static Palace forTeam(Team team) {
        if (team == Team.CHO) {
            return CHO_PALACE;
        }
        return HAN_PALACE;
    }

    public static List<Palace> all() {
        return ALL;
    }
}
