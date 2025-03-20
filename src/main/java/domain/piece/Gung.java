package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.GungMovement;
import java.util.Arrays;
import java.util.List;

public class Gung extends Piece {
    public Gung(Country country) {
        super(country, PieceType.GUNG);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        return Arrays.stream(GungMovement.values())
                .map(gungMovement -> movePosition(currCoordinate, gungMovement.getDirection()))
                .filter(next -> !janggiBoard.isOutOfBoundary(next) && !janggiBoard.isMyTeam(currCoordinate, next))
                .toList();
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
