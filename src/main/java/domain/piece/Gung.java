package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.GungMovement;
import java.util.ArrayList;
import java.util.List;

public class Gung implements Piece {
    private final Country country;

    public Gung(Country country) {
        this.country = country;
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();

        for (GungMovement gungMovement : GungMovement.values()) {
            JanggiCoordinate next = movePosition(currCoordinate, gungMovement.getDirection());
            if (!janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                availablePositions.add(next);
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
