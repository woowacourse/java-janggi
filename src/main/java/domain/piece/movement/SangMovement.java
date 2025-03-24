package domain.piece.movement;

import domain.Coordinate;
import java.util.List;

public enum SangMovement {

    UP(new Coordinate(-1, 0),
            List.of(new Coordinate(-2, -1), new Coordinate(-3, -2)),
            List.of(new Coordinate(-2, 1), new Coordinate(-3, 2))),

    DOWN(new Coordinate(1, 0),
            List.of(new Coordinate(2, -1), new Coordinate(3, -2)),
            List.of(new Coordinate(2, 1), new Coordinate(3, 2))),

    RIGHT(new Coordinate(0, 1),
            List.of(new Coordinate(-1, 2), new Coordinate(-2, 3)),
            List.of(new Coordinate(1, 2), new Coordinate(2, 3))),

    LEFT(new Coordinate(0, -1),
            List.of(new Coordinate(1, -2), new Coordinate(2, -3)),
            List.of(new Coordinate(-1, -2), new Coordinate(-2, -3)));

    private final Coordinate direction;
    private final List<Coordinate> leftDestination;
    private final List<Coordinate> rightDestination;

    SangMovement(Coordinate direction, List<Coordinate> leftDestination, List<Coordinate> rightDestination) {
        this.direction = direction;
        this.leftDestination = leftDestination;
        this.rightDestination = rightDestination;
    }

    public Coordinate getDirection() {
        return direction;
    }

    public List<Coordinate> getLeftDestination() {
        return leftDestination;
    }

    public List<Coordinate> getRightDestination() {
        return rightDestination;
    }
}
