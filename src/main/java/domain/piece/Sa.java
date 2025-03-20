package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.SaMovement;
import java.util.Arrays;
import java.util.List;

public class Sa extends Piece {
    
    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        return Arrays.stream(SaMovement.values())
                .map(gungMovement -> movePosition(currCoordinate, gungMovement.getDirection()))
                .filter(next -> !janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next))
                .toList();
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
