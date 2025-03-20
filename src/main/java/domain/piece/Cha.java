package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.ChaMovement;
import java.util.ArrayList;
import java.util.List;

import static domain.piece.PieceType.*;

public class Cha extends Piece {
    public Cha(Country country) {
        super(country, CHA);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate,
                                                         JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (ChaMovement direction : ChaMovement.values()) {
            JanggiCoordinate next = movePosition(currCoordinate, direction.getDirection());
            while (true) {
                if (janggiBoard.isOutOfBoundary(next)) {
                    break;
                }
                if (janggiBoard.hasPiece(next) && !janggiBoard.isMyTeam(currCoordinate, next)) {
                    availablePositions.add(next);
                    break;
                }
                if (janggiBoard.hasPiece(next) && janggiBoard.isMyTeam(currCoordinate, next)) {
                    break;
                }

                availablePositions.add(next);
                next = movePosition(next, direction.getDirection());
            }
        }

        return availablePositions;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }
}
