package domain.movement;

import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class SlidingPath {
    private final Direction direction;

    public SlidingPath(Direction direction) {
        this.direction = direction;
    }

    public List<Position> pathPositions(Position departure, Position destination) {
        List<Position> positions = new ArrayList<>();
        Position current = departure;

        while (!direction.move(current).equals(destination)) {
            current = direction.move(current);
            positions.add(current);
        }
        return positions;
    }
}
