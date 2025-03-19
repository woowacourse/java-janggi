package domain.piece.movement;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import java.util.ArrayList;
import java.util.List;

public enum Movement {

    UP(new JanggiCoordinate(0, -1), List.of(new JanggiCoordinate(-1, -2), new JanggiCoordinate(1, -2))),
    DOWN(new JanggiCoordinate(0, 1), List.of(new JanggiCoordinate(2, -1), new JanggiCoordinate(2, 1))),
    RIGHT(new JanggiCoordinate(1, 0), List.of(new JanggiCoordinate(1, 2), new JanggiCoordinate(1, -2))),
    LEFT(new JanggiCoordinate(-1, 0), List.of(new JanggiCoordinate(-2, 1), new JanggiCoordinate(-2, -1)));

    private final JanggiCoordinate direction;
    private final List<JanggiCoordinate> destination;

    Movement(JanggiCoordinate direction, List<JanggiCoordinate> destination) {
        this.direction = direction;
        this.destination = destination;
    }

    public static List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                                JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (Movement movement : values()) {
            if (!janggiBoard.hasPiece(movePosition(currCoordinate, movement.direction))) {
                for (JanggiCoordinate destination : movement.destination) {
                    JanggiCoordinate next = movePosition(currCoordinate, destination);
                    if (!janggiBoard.hasPiece(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                        availablePositions.add(next);
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
