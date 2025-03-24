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
        for (Direction direction : directions) {
            if(!target.canMove(direction, board)){
                return false;
            };
            target = target.move(direction);
        }
        return true;
    }
}
