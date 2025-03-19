package domain.piece.movement;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.ArrayList;
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

    public static List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                                JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (SangMovement sangMovement : values()) {
            if (!janggiBoard.hasPiece(movePosition(currCoordinate, sangMovement.direction))) {
                for (JanggiCoordinate subDirection : sangMovement.subDirection) {
                    if (!janggiBoard.hasPiece(movePosition(currCoordinate, subDirection))) {
                        for (JanggiCoordinate destination : sangMovement.destination) {
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
            }
        }
        return availablePositions;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
