package domain.piece.movement;

import domain.JanggiCoordinate;
import java.util.List;

public enum SangMovement {

    UP(new JanggiCoordinate(0, -1),
            List.of(new JanggiCoordinate(-1, -2), new JanggiCoordinate(-2, -3)),
            List.of(new JanggiCoordinate(1, -2), new JanggiCoordinate(2, -3))),

    DOWN(new JanggiCoordinate(0, 1),
            List.of(new JanggiCoordinate(1, 2), new JanggiCoordinate(2, 3)),
            List.of(new JanggiCoordinate(-1, 2), new JanggiCoordinate(-2, 3))),

    RIGHT(new JanggiCoordinate(1, 0),
            List.of(new JanggiCoordinate(2, -1), new JanggiCoordinate(3, -2)),
            List.of(new JanggiCoordinate(2, 1), new JanggiCoordinate(3, 2))),

    LEFT(new JanggiCoordinate(-1, 0),
            List.of(new JanggiCoordinate(-2, -1), new JanggiCoordinate(-3, -2)),
            List.of(new JanggiCoordinate(-2, 1), new JanggiCoordinate(-3, 2)));

    private final JanggiCoordinate direction;
    private final List<JanggiCoordinate> subDirection;
    private final List<JanggiCoordinate> destination;

    SangMovement(JanggiCoordinate direction, List<JanggiCoordinate> subDirection,
                 List<JanggiCoordinate> destination) {
        this.direction = direction;
        this.subDirection = subDirection;
        this.destination = destination;
    }

    public JanggiCoordinate getDirection() {
        return direction;
    }

    public List<JanggiCoordinate> getSubDirection() {
        return subDirection;
    }

    public List<JanggiCoordinate> getDestination() {
        return destination;
    }
}
