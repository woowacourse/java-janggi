package domain.piece;

import domain.Coordinate;
import domain.board.Board;
import domain.piece.movement.Movement;
import java.util.ArrayList;
import java.util.List;

public class Pho extends Piece {

    private final List<Movement> MOVEMENTS = List.of(
            Movement.UP, Movement.DOWN, Movement.RIGHT, Movement.LEFT);

    public Pho(Country country) {
        super(country, PieceType.PHO);
    }

    @Override
    public List<Coordinate> availableMovePositions(Coordinate from, Board board) {
        List<Coordinate> availablePositions = new ArrayList<>();
        for (Movement movement : MOVEMENTS) {
            Coordinate next = from.move(movement);
            boolean hasObstacle = false;
            while (true) {
                if (invalidPhoCoordinate(board, next, hasObstacle)) {
                    break;
                }
                if (isBlankCoordinateAndReachable(board, next, hasObstacle)) {
                    availablePositions.add(next);
                }
                if (isEnemyAndReachable(board, next, hasObstacle)) {
                    availablePositions.add(next);
                    break;
                }
                if (isObstacle(board, next, hasObstacle)) {
                    hasObstacle = true;
                }
                next = next.move(movement);
            }
        }
        return availablePositions;
    }

    private boolean isObstacle(Board board,
                               Coordinate next,
                               boolean hasObstacle) {
        return board.hasPiece(next) && !hasObstacle && !board.isPho(next);
    }

    private boolean isEnemyAndReachable(Board board,
                                        Coordinate next,
                                        boolean hasObstacle) {
        return board.hasPiece(next) && hasObstacle && !board.isMyTeam(country, next);
    }

    private boolean isBlankCoordinateAndReachable(Board board,
                                                  Coordinate next,
                                                  boolean hasObstacle) {
        return !board.hasPiece(next) && hasObstacle;
    }

    private boolean invalidPhoCoordinate(Board board,
                                         Coordinate next,
                                         boolean hasObstacle) {
        return next.isOutOfBoundary() ||
                (board.hasPiece(next) && board.isPho(next)) ||
                board.hasPiece(next) && hasObstacle && board.isMyTeam(country, next);
    }

    @Override
    public boolean isPho() {
        return true;
    }
}
