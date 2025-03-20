package domain.piece.movement;

import domain.JanggiCoordinate;

public enum PhoMovement {
    UP(new JanggiCoordinate(-1, 0)),
    DOWN(new JanggiCoordinate(1, 0)),
    RIGHT(new JanggiCoordinate(0, 1)),
    LEFT(new JanggiCoordinate(0, -1));

    private final JanggiCoordinate direction;

    PhoMovement(JanggiCoordinate direction) {
        this.direction = direction;
    }


    public JanggiCoordinate getDirection() {
        return direction;
    }
}
