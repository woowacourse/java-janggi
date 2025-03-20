package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.SaMovement;

import java.util.ArrayList;
import java.util.List;

public class Sa extends Piece {
    public Sa(Country country) {
        super(country, PieceType.SA);
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
}
