package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.SangMovement;
import java.util.Arrays;
import java.util.List;

public class Sang extends Piece {
    
    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        return Arrays.stream(SangMovement.values())
                .filter(sangMovement -> !janggiBoard.hasPiece(
                        movePosition(currCoordinate, sangMovement.getDirection())))
                .flatMap(sangMovement -> sangMovement.getLeftDestination().stream()
                        .filter(subDirection -> !janggiBoard.hasPiece(movePosition(currCoordinate, subDirection)))
                        .flatMap(subDirection -> sangMovement.getRightDestination().stream()
                                .map(destination -> movePosition(currCoordinate, destination))
                                .filter(next -> !janggiBoard.isOutOfBoundary(next))
                                .filter(next -> !janggiBoard.hasPiece(next) || !janggiBoard.isMyTeam(currCoordinate,
                                        next))
                        )
                )
                .toList();
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
