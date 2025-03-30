package janggi.game;

import static janggi.movement.direction.Direction.EAST;
import static janggi.movement.direction.Direction.NORTH;
import static janggi.movement.direction.Direction.NORTH_EAST;
import static janggi.movement.direction.Direction.NORTH_WEST;
import static janggi.movement.direction.Direction.SOUTH;
import static janggi.movement.direction.Direction.SOUTH_EAST;
import static janggi.movement.direction.Direction.SOUTH_WEST;
import static janggi.movement.direction.Direction.WEST;

import janggi.movement.direction.Direction;
import janggi.piece.Piece;
import janggi.point.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Palace {
    private static final List<Area> palace;

    static {
        palace = Arrays.stream(Team.values()).map(Area::of).toList();
    }

    public static boolean movesInPalaceOfMyTeam(Piece movingPiece, Point targetPoint) {
        Point movingPoint = movingPiece.getPoint();
        Team movingTeam = movingPiece.getTeam();
        boolean containsMovingPoint = false;
        Team palaceTeam = Team.CHO;
        boolean containsTargetPoint = false;
        for (Area area : palace) {
            if (area.contains(movingPoint)) {
                containsMovingPoint = true;
                palaceTeam = area.getTeam();
            }
            if (area.contains(targetPoint)) {
                containsTargetPoint = true;
            }
        }
        return containsMovingPoint
                && containsTargetPoint
                && movingTeam == palaceTeam;
    }

    public static boolean movesInPalace(Point movingPoint, Point targetPoint) {
        boolean containsMovingPoint = false;
        boolean containsTargetPoint = false;
        for (Area area : palace) {
            if (area.contains(movingPoint)) {
                containsMovingPoint = true;
            }
            if (area.contains(targetPoint)) {
                containsTargetPoint = true;
            }
        }
        return containsMovingPoint && containsTargetPoint;
    }

    public static boolean movesOnEdge(Point movingPoint, Direction direction) {
        for (Area area : palace) {
            if (area.contains(movingPoint)) {
                return area.hasEdgeFrom(movingPoint, direction);
            }
        }
        return false;
    }

    private static class Area {
        private final Map<Point, List<Direction>> nodes;
        private final Team team;

        private static Area of(Team team) {
            if (team.isCho()) {
                return new Area(Map.of(
                        new Point(7, 3), List.of(SOUTH, EAST, SOUTH_EAST),
                        new Point(7, 4), List.of(WEST, EAST, SOUTH),
                        new Point(7, 5), List.of(WEST, SOUTH, SOUTH_WEST),
                        new Point(8, 3), List.of(NORTH, SOUTH, EAST),
                        new Point(8, 4),
                        List.of(NORTH, SOUTH, WEST, EAST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST),
                        new Point(8, 5), List.of(NORTH, SOUTH, WEST),
                        new Point(9, 3), List.of(NORTH, EAST, NORTH_EAST),
                        new Point(9, 4), List.of(NORTH, WEST, EAST),
                        new Point(9, 5), List.of(NORTH, WEST, NORTH_WEST)
                ), team);
            }
            return new Area(Map.of(
                    new Point(0, 3), List.of(SOUTH, EAST, SOUTH_EAST),
                    new Point(0, 4), List.of(WEST, EAST, SOUTH),
                    new Point(0, 5), List.of(WEST, SOUTH, SOUTH_WEST),
                    new Point(1, 3), List.of(NORTH, SOUTH, EAST),
                    new Point(1, 4), List.of(NORTH, SOUTH, EAST, WEST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST),
                    new Point(1, 5), List.of(NORTH, SOUTH, WEST),
                    new Point(2, 3), List.of(NORTH, EAST, NORTH_EAST),
                    new Point(2, 4), List.of(NORTH, WEST, EAST),
                    new Point(2, 5), List.of(NORTH, WEST, NORTH_WEST)
            ), team);
        }

        public Area(Map<Point, List<Direction>> nodes, Team team) {
            this.nodes = nodes;
            this.team = team;
        }

        public boolean contains(Point movingPoint) {
            return nodes.containsKey(movingPoint);
        }

        public boolean hasEdgeFrom(Point movingPoint, Direction direction) {
            return nodes.get(movingPoint).contains(direction);
        }

        public Team getTeam() {
            return team;
        }
    }
}
