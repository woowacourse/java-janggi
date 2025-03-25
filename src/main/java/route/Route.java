package route;

import java.util.List;
import java.util.stream.Stream;
import position.Board;
import position.Position;

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

    public Route add(Route route) {
        return new Route(Stream.concat(directions.stream(), route.directions.stream()).toList());
    }

    public List<Direction> route() {
        return directions;
    }
}


