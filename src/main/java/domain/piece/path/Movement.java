package domain.piece.path;

import domain.position.Direction;
import java.util.List;

public class Movement {
    private final List<Direction> movement;

    public Movement(List<Direction> movement) {
        this.movement = movement;
    }

    public List<Direction> getMovement() {
        return movement;
    }
}
