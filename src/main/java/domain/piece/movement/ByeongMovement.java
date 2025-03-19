package domain.piece.movement;

import domain.JanggiCoordinate;

public enum ByeongMovement {
    UP(new JanggiCoordinate(0, -1)),
    DOWN(new JanggiCoordinate(0, 1)),
    RIGHT(new JanggiCoordinate(1, 0)),
    LEFT(new JanggiCoordinate(-1, 0));

    private final JanggiCoordinate direction;

    ByeongMovement(JanggiCoordinate direction) {
        this.direction = direction;
    }

    public JanggiCoordinate getDirection() {
        return direction;
    }
}
