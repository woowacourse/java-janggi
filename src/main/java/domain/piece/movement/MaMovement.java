package domain.piece.movement;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.ArrayList;
import java.util.List;

import static domain.piece.movement.SangMovement.movePosition;

public enum MaMovement{

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

    public static List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                                JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (MaMovement maMovement : values()) {
            if (!janggiBoard.hasPiece(movePosition(currCoordinate, maMovement.getDirection()))) {
                for (JanggiCoordinate destination : maMovement.getDestination()) {
                    JanggiCoordinate next = movePosition(currCoordinate, destination);
                    if (janggiBoard.isOutOfBoundary(next)) {
                        continue;
                    }
                    if (!janggiBoard.hasPiece(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                        availablePositions.add(next);
                    }
                }
            }
        }
        return availablePositions;
    }

    public JanggiCoordinate getDirection() {
        return direction;
    }

    public List<JanggiCoordinate> getDestination() {
        return destination;
    }
}
