package domain.piece.movement;

import domain.JanggiCoordinate;

public enum SaMovement {

    UP(new JanggiCoordinate(-1, 0)),
    UP_RIGHT(new JanggiCoordinate(-1, 1)),
    RIGHT(new JanggiCoordinate(0, 1)),
    DOWN_RIGHT(new JanggiCoordinate(1, 1)),
    DOWN(new JanggiCoordinate(1, 0)),
    DOWN_LEFT(new JanggiCoordinate(1, -1)),
    LEFT(new JanggiCoordinate(0, -1)),
    UP_LEFT(new JanggiCoordinate(-1, -1));

    private final JanggiCoordinate direction;

    SaMovement(JanggiCoordinate direction) {
        this.direction = direction;
    }

    public JanggiCoordinate getDirection() {
        return direction;
    }
}
