package route;

import java.util.List;
import position.Board;
import position.Position;

public final class Route {
    private final List<Direction> directions;

    public Route(List<Direction> directions) {
        this.directions = directions;
    }

    public boolean isPossibleRoute(Position source, Board board) {
        for (Direction direction : directions) {
            if(!source.canMove(direction, board)){
                return false;
            };
        }
        return true;
    }
}
