package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.SangMovement;
import java.util.ArrayList;
import java.util.List;

public class Sang extends Piece {

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (SangMovement sangMovement : SangMovement.values()) {
            JanggiCoordinate next = movePosition(currCoordinate, sangMovement.getDirection());
            if (!janggiBoard.hasPiece(next)) {
                List<JanggiCoordinate> left = sangMovement.getLeftDestination();
                if (!janggiBoard.hasPiece(movePosition(currCoordinate, left.get(0))) &&
                        (!janggiBoard.hasPiece(movePosition(currCoordinate, left.get(1))) || !janggiBoard.isMyTeam(
                                currCoordinate, movePosition(currCoordinate, left.get(1))))) {
                    availablePositions.add(movePosition(currCoordinate, left.get(1)));
                }
                
                List<JanggiCoordinate> right = sangMovement.getRightDestination();
                if (!janggiBoard.hasPiece(movePosition(currCoordinate, right.get(0))) &&
                        (!janggiBoard.hasPiece(movePosition(currCoordinate, right.get(1))) || !janggiBoard.isMyTeam(
                                currCoordinate, movePosition(currCoordinate, right.get(1))))) {
                    availablePositions.add(movePosition(currCoordinate, right.get(1)));
                }
            }
        }
        return availablePositions;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
