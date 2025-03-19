package domain.piece.movement;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.ArrayList;
import java.util.List;

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

    public static List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                                JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();

        for (GungMovement gungMovement : values()) {
            JanggiCoordinate next = movePosition(currCoordinate, gungMovement.direction);
            if (!janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                availablePositions.add(next);
            }
        }
        return availablePositions;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
