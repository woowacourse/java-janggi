package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.SangMovement;
import java.util.ArrayList;
import java.util.List;

public class Sang implements Piece {

    private static final List<JanggiCoordinate> OFFSET_MOVEMENT = List.of(
            new JanggiCoordinate(3, 2),
            new JanggiCoordinate(3, -2),
            new JanggiCoordinate(-3, 2),
            new JanggiCoordinate(-3, -2),
            new JanggiCoordinate(2, 3),
            new JanggiCoordinate(2, -3),
            new JanggiCoordinate(-2, 3),
            new JanggiCoordinate(-2, -3)
    );

    private final Country country;

    public Sang(Country country) {
        this.country = country;
    }

    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (SangMovement sangMovement : SangMovement.values()) {
            if (!janggiBoard.hasPiece(movePosition(currCoordinate, sangMovement.getDirection()))) {
                for (JanggiCoordinate subDirection : sangMovement.getSubDirection()) {
                    if (!janggiBoard.hasPiece(movePosition(currCoordinate, subDirection))) {
                        for (JanggiCoordinate destination : sangMovement.getDestination()) {
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

    @Override
    public boolean isPho() {
        return false;
    }

    @Override
    public Country getTeam() {
        return country;
    }
}
