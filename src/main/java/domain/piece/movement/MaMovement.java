package domain.piece.movement;

import domain.Coordinate;
import java.util.List;

public enum MaMovement {

    UP(new Coordinate(-1, 0), List.of(new Coordinate(-2, -1), new Coordinate(-2, 1))),
    DOWN(new Coordinate(1, 0), List.of(new Coordinate(2, 1), new Coordinate(2, -1))),
    RIGHT(new Coordinate(0, 1), List.of(new Coordinate(-1, 2), new Coordinate(1, 2))),
    LEFT(new Coordinate(0, -1), List.of(new Coordinate(1, -2), new Coordinate(-1, -2)));

    private final Coordinate direction;
    private final List<Coordinate> destination;

    MaMovement(Coordinate direction, List<Coordinate> destination) {
        this.direction = direction;
        this.destination = destination;
    }

    public Coordinate getDirection() {
        return direction;
    }

    public List<Coordinate> getDestination() {
        return destination;
    }
}
