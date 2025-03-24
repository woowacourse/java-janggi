package route;

import java.util.List;
import position.Board;
import position.Position;

public final class Route {
    private final List<Direction> directions;

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

    public boolean isPossibleRoute(final Position source, Board board) {
        Position target = source;
        for (int directionCount = 0; directionCount < directions.size()-1; directionCount++) {
            if(!target.canMove(directions.get(directionCount), board)){
                return false;
            };
            target = target.move(directions.get(directionCount));
        }

        if(!target.canMoveLast(directions.getLast(), board)){
            return false;
        };
        return true;
    }
}
