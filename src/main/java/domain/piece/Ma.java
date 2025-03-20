package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.MaMovement;
import java.util.Arrays;
import java.util.List;

public class Ma extends Piece {
    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        return Arrays.stream(MaMovement.values())
                .filter(maMovement -> !janggiBoard.hasPiece(movePosition(currCoordinate, maMovement.getDirection())))
                .flatMap(maMovement -> maMovement.getDestination().stream()
                        .map(destination -> movePosition(currCoordinate, destination))
                        .filter(next -> !janggiBoard.isOutOfBoundary(next))
                        .filter(next -> !janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate, next))
                )
                .toList();
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
