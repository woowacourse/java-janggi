package janggi.route;

import java.util.List;
import java.util.stream.Stream;
import janggi.position.Board;
import janggi.position.Position;

public final class Route {
    private final List<Direction> directions;

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

    public boolean isPossibleRoute(final Position source, Board board) {
        Position target = source;
        for (int directionCount = 0; directionCount < directions.size() - 1; directionCount++) {
            if (!target.canMove(directions.get(directionCount), board)) {
                return false;
            }
            target = target.move(directions.get(directionCount));
        }

        return target.canMoveLast(directions.getLast(), board);
    }

    public boolean isPossibleRouteForCannon(Position source, Board board) {
        Position target = source;
        for (int directionCount = 0; directionCount < directions.size() - 1; directionCount++) {
            if (!target.canMove(directions.get(directionCount), board)) {
                return false;
            }
            target = target.move(directions.get(directionCount));
        }

        return target.canMoveLastForCannon(directions.getLast(), board);
    }

    public boolean canJump(Position source, Board board) {
        Position target = source;
        for (Direction direction : directions) {
            if (target.canJump(direction, board)) { // 1칸뒤에 포가 아닌 기물이 있어야 함. 2칸뒤로 이동할 수 있어야 함.
                return true;
            }
            if(!target.canMove(direction, board)){
                return false;
            }
            target = target.move(direction);
        }
        return false;
    }

    public Route add(Route route) {
        return new Route(Stream.concat(directions.stream(), route.directions.stream()).toList());
    }

    public List<Direction> route() {
        return directions;
    }

}


