package domain.piece.movement;

import domain.JanggiCoordinate;
import java.util.List;

public enum MaMovement {

    UP(new JanggiCoordinate(0, -1), List.of(new JanggiCoordinate(-1, -2), new JanggiCoordinate(1, -2))),
    DOWN(new JanggiCoordinate(0, 1), List.of(new JanggiCoordinate(1, 2), new JanggiCoordinate(-1, 2))),
    RIGHT(new JanggiCoordinate(1, 0), List.of(new JanggiCoordinate(2, -1), new JanggiCoordinate(2, 1))),
    LEFT(new JanggiCoordinate(-1, 0), List.of(new JanggiCoordinate(-2, 1), new JanggiCoordinate(-2, -1)));

    private final JanggiCoordinate direction;
    private final List<JanggiCoordinate> destination;

    MaMovement(JanggiCoordinate direction, List<JanggiCoordinate> destination) {
        this.direction = direction;
        this.destination = destination;
    }

    public JanggiCoordinate getDirection() {
        return direction;
    }

    public List<JanggiCoordinate> getDestination() {
        return destination;
    }
}
