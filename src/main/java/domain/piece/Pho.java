package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.PhoMovement;
import java.util.ArrayList;
import java.util.List;

public class Pho extends Piece {
    public Pho(Country country) {
        super(country,PieceType.PHO);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate, JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (PhoMovement direction : PhoMovement.values()) {
            JanggiCoordinate next = movePosition(currCoordinate, direction.getDirection());
            boolean hasObstacle = false;
            while (true) {
                if (janggiBoard.isOutOfBoundary(next)) {
                    break;
                }
                if (!janggiBoard.hasPiece(next) && hasObstacle) {
                    availablePositions.add(next);
                }
                if (janggiBoard.hasPiece(next) && janggiBoard.isPho(next)) {
                    break;
                }
                if (janggiBoard.hasPiece(next) && hasObstacle && janggiBoard.isMyTeam(currCoordinate, next)) {
                    break;
                }
                if (janggiBoard.hasPiece(next) && hasObstacle && !janggiBoard.isMyTeam(currCoordinate, next)) {
                    availablePositions.add(next);
                    break;
                }
                if (janggiBoard.hasPiece(next) && !hasObstacle && !janggiBoard.isPho(next)) {
                    hasObstacle = true;
                }
                next = movePosition(next, direction.getDirection());
            }
        }

        return availablePositions;
    }

    public static JanggiCoordinate movePosition(JanggiCoordinate currCoordinate, JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }

    @Override
    public boolean isPho() {
        return true;
    }
}
