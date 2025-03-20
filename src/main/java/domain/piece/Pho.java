package domain.piece;

import domain.JanggiCoordinate;
import domain.board.JanggiBoard;
import domain.piece.movement.PhoMovement;

import java.util.ArrayList;
import java.util.List;

public class Pho extends Piece {

    public Pho(Country country) {
        super(country, PieceType.PHO);
    }

    @Override
    public List<JanggiCoordinate> availableMovePositions(JanggiCoordinate currCoordinate, JanggiBoard janggiBoard) {
        List<JanggiCoordinate> availablePositions = new ArrayList<>();
        for (PhoMovement direction : PhoMovement.values()) {
            JanggiCoordinate next = movePosition(currCoordinate, direction.getDirection());
            boolean hasObstacle = false;
            while (true) {
                if (invalidPhoCoordinate(currCoordinate, janggiBoard, next, hasObstacle)) {
                    break;
                }
                if (isBlankCoordinateAndReachable(janggiBoard, next, hasObstacle)) {
                    availablePositions.add(next);
                }
                if (isEnemyAndReachable(currCoordinate, janggiBoard, next, hasObstacle)) {
                    availablePositions.add(next);
                    break;
                }
                if (isObstacle(janggiBoard, next, hasObstacle)) {
                    hasObstacle = true;
                }
                next = movePosition(next, direction.getDirection());
            }
        }
        return availablePositions;
    }

    private boolean isObstacle(JanggiBoard janggiBoard,
                               JanggiCoordinate next,
                               boolean hasObstacle) {
        return janggiBoard.hasPiece(next) && !hasObstacle && !janggiBoard.isPho(next);
    }

    private boolean isEnemyAndReachable(JanggiCoordinate currCoordinate,
                                        JanggiBoard janggiBoard,
                                        JanggiCoordinate next,
                                        boolean hasObstacle) {
        return janggiBoard.hasPiece(next) && hasObstacle && !janggiBoard.isMyTeam(currCoordinate, next);
    }

    private boolean isBlankCoordinateAndReachable(JanggiBoard janggiBoard,
                                                  JanggiCoordinate next,
                                                  boolean hasObstacle) {
        return !janggiBoard.hasPiece(next) && hasObstacle;
    }

    private boolean invalidPhoCoordinate(JanggiCoordinate currCoordinate,
                                         JanggiBoard janggiBoard,
                                         JanggiCoordinate next,
                                         boolean hasObstacle) {
        return janggiBoard.isOutOfBoundary(next) ||
                (janggiBoard.hasPiece(next) && janggiBoard.isPho(next)) ||
                janggiBoard.hasPiece(next) && hasObstacle && janggiBoard.isMyTeam(currCoordinate, next);
    }

    public JanggiCoordinate movePosition(JanggiCoordinate currCoordinate,
                                         JanggiCoordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }

    @Override
    public boolean isPho() {
        return true;
    }
}
