package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.SaMovement;
import java.util.ArrayList;
import java.util.List;

public class Sa implements Piece {
    private final Country country;

    public Sa(Country country) {
        this.country = country;
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();

        for (SaMovement saMovement : SaMovement.values()) {
            JanggiCoordinate next = movePosition(currCoordinate, saMovement.getDirection());
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
