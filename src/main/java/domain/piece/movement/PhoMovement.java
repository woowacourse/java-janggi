package domain.piece.movement;

import domain.JanggiCoordinate;

public enum PhoMovement {
    UP(new JanggiCoordinate(0, -1)),
    DOWN(new JanggiCoordinate(0, 1)),
    RIGHT(new JanggiCoordinate(1, 0)),
    LEFT(new JanggiCoordinate(-1, 0));

    private final JanggiCoordinate direction;

    PhoMovement(JanggiCoordinate direction) {
        this.direction = direction;
    }


    public JanggiCoordinate getDirection() {
        return direction;
    }
}
