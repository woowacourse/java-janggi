package domain.piece.movement;

import domain.JanggiCoordinate;

public enum GungMovement {

    UP(new JanggiCoordinate(0, -1)),
    UP_RIGHT(new JanggiCoordinate(1, -1)),
    RIGHT(new JanggiCoordinate(1, 0)),
    DOWN_RIGHT(new JanggiCoordinate(1, 1)),
    DOWN(new JanggiCoordinate(0, 1)),
    DOWN_LEFT(new JanggiCoordinate(-1, 1)),
    LEFT(new JanggiCoordinate(-1, 0)),
    UP_LEFT(new JanggiCoordinate(-1, -1));

    private final JanggiCoordinate direction;

    GungMovement(JanggiCoordinate direction) {
        this.direction = direction;
    }

    public JanggiCoordinate getDirection() {
        return direction;
    }
}
