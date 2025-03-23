package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;

public class Pho extends Piece {

    private final List<Movement> movements = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT);

    public Pho(Country country) {
        super(country, PieceType.PHO);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate currCoordinate, Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();
        for (Movement movement : movements) {
            Coordinate next = movePosition(currCoordinate, movement.getDirection());
            boolean hasObstacle = false;
            while (true) {
                if (invalidPhoCoordinate(currCoordinate, board, next, hasObstacle)) {
                    break;
                }
                if (isBlankCoordinateAndReachable(board, next, hasObstacle)) {
                    availablePositions.add(next);
                }
                if (isEnemyAndReachable(currCoordinate, board, next, hasObstacle)) {
                    availablePositions.add(next);
                    break;
                }
                if (isObstacle(board, next, hasObstacle)) {
                    hasObstacle = true;
                }
                next = movePosition(next, movement.getDirection());
            }
        }
        return availablePositions;
    }

    private boolean isObstacle(Board board,
                               Coordinate next,
                               boolean hasObstacle) {
        return board.hasPiece(next) && !hasObstacle && !board.isPho(next);
    }

    private boolean isEnemyAndReachable(Coordinate currCoordinate,
                                        Board board,
                                        Coordinate next,
                                        boolean hasObstacle) {
        return board.hasPiece(next) && hasObstacle && !board.isMyTeam(currCoordinate, next);
    }

    private boolean isBlankCoordinateAndReachable(Board board,
                                                  Coordinate next,
                                                  boolean hasObstacle) {
        return !board.hasPiece(next) && hasObstacle;
    }

    private boolean invalidPhoCoordinate(Coordinate currCoordinate,
                                         Board board,
                                         Coordinate next,
                                         boolean hasObstacle) {
        return next.isOutOfBoundary() ||
                (board.hasPiece(next) && board.isPho(next)) ||
                board.hasPiece(next) && hasObstacle && board.isMyTeam(currCoordinate, next);
    }

    public Coordinate movePosition(Coordinate currCoordinate,
                                   Coordinate moveOffset) {
        return currCoordinate.move(moveOffset.getRow(), moveOffset.getCol());
    }

    @Override
    public boolean isPho() {
        return true;
    }
}
