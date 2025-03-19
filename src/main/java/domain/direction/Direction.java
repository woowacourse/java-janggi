package domain.direction;

import domain.piece.Position;
import java.util.List;

public class Direction {

    private final List<Position> direction;

    public Direction(List<Position> direction) {
        this.direction = direction;
    }

    public boolean canReach(Position start, Position target) {
        Position result = start;
        for (Position dir : direction) {
            result = result.merge(dir);
        }
        return result.equals(target);
    }
}
