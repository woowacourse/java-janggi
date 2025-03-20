package domain.piece.movement;

import domain.JanggiCoordinate;
import java.util.List;

public enum SangMovement {

    UP(new JanggiCoordinate(0, -1),
            List.of(new JanggiCoordinate(-1, -2), new JanggiCoordinate(-2, -3)),
            List.of(new JanggiCoordinate(1, -2), new JanggiCoordinate(2, -3))),

    DOWN(new JanggiCoordinate(0, 1),
            List.of(new JanggiCoordinate(-1, 2), new JanggiCoordinate(-2, 3)),
            List.of(new JanggiCoordinate(1, 2), new JanggiCoordinate(2, 3))),

    RIGHT(new JanggiCoordinate(1, 0),
            List.of(new JanggiCoordinate(2, -1), new JanggiCoordinate(3, -2)),
            List.of(new JanggiCoordinate(2, 1), new JanggiCoordinate(3, 2))),

    LEFT(new JanggiCoordinate(-1, 0),
            List.of(new JanggiCoordinate(-2, 1), new JanggiCoordinate(-3, 2)),
            List.of(new JanggiCoordinate(-2, -1), new JanggiCoordinate(-3, -2)));

    private final JanggiCoordinate direction;
    private final List<JanggiCoordinate> leftDestination;
    private final List<JanggiCoordinate> rightDestination;

    SangMovement(JanggiCoordinate direction, List<JanggiCoordinate> leftDestination,
                 List<JanggiCoordinate> rightDestination) {
        this.direction = direction;
        this.leftDestination = leftDestination;
        this.rightDestination = rightDestination;
    }

    public JanggiCoordinate getDirection() {
        return direction;
    }

    public List<JanggiCoordinate> getLeftDestination() {
        return leftDestination;
    }

    public List<JanggiCoordinate> getRightDestination() {
        return rightDestination;
    }
}
