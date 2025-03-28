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
import janggi.piece.Movable;
import janggi.point.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Palace {
    private static final Map<Team, Area> palace;

    static {
        Map<Team, Area> palacePerTeam = new HashMap<>();
        for (Team team : Team.values()) {
            palacePerTeam.put(team, Area.of(team));
        }
        palace = palacePerTeam;
    }

    public static boolean movesInPalace(Movable movingPiece, Point targetPoint) {
        Point movingPoint = movingPiece.getPoint();
        Area area = palace.get(movingPiece.getTeam());
        return area.contains(movingPoint) && area.contains(targetPoint);
    }

    public static boolean movesOnEdge(Movable movingPiece, Direction direction) {
        Point movingPoint = movingPiece.getPoint();
        Area area = palace.get(movingPiece.getTeam());
        if (area.contains(movingPoint)) {
            if (area.hasEdgeFrom(movingPoint, direction)) {
                return true;
            }
        }
        return false;
    }

    private static class Area {
        private final Map<Point, List<Direction>> nodes;
        //TODO: Link를 할까 Direction을 할까?
        /**
         * 1. Direction을 구한다 > 여기서 잡지 않으면 에러가 날것..
         * 2. Route를 구해서 간선에 포함되는지 확인한다
         */

        private static Area of(Team team) {
            if (team.isCho()) {
                return new Area(Map.of(
                        new Point(7, 3), List.of(SOUTH, EAST, SOUTH_EAST),
                        new Point(7, 4), List.of(WEST, EAST, SOUTH),
                        new Point(7, 5), List.of(WEST, SOUTH, SOUTH_WEST),
                        new Point(8, 3), List.of(NORTH, SOUTH, EAST),
                        new Point(8, 4), List.of(NORTH, SOUTH, WEST, EAST, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST),
                        new Point(8, 5), List.of(NORTH, SOUTH, WEST),
                        new Point(9, 3), List.of(NORTH, EAST, NORTH_EAST),
                        new Point(9, 4), List.of(NORTH, WEST, EAST),
                        new Point(9, 5), List.of(NORTH, WEST, NORTH_WEST)
                ));
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
            ));
        }

        public Area(Map<Point, List<Direction>> nodes) {
            this.nodes = nodes;
        }

        public boolean contains(Point movingPoint) {
            return nodes.containsKey(movingPoint);
        }

        public boolean hasEdgeFrom(Point movingPoint, Direction direction) {
            return nodes.get(movingPoint).contains(direction);
        }
    }
}
