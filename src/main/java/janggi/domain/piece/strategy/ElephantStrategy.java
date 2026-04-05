package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class ElephantStrategy implements MoveStrategy {

    private static final int STRAIGHT_DISTANCE = 1;
    private static final int DIAGONAL_DISTANCE = 2;

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceStrategy pieceStrategy) {
        Movement movement = new Movement(source, destination);
        validateDistance(movement);
        List<Position> path = findPath(source, movement);
        List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
        validatePath(pathBeforeDestination, board);
    }

    private void validateDistance(Movement movement) {
        if (movement.isInvalidMoveDistance(DIAGONAL_DISTANCE, STRAIGHT_DISTANCE + DIAGONAL_DISTANCE)) {
            throw new IllegalArgumentException(
                    ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(STRAIGHT_DISTANCE, DIAGONAL_DISTANCE)
            );
        }
    }

    private List<Position> findPath(Position source, Movement movement) {
        List<Position> path = new ArrayList<>();

        Position first = moveStraightStep(source, movement);
        path.add(first);
        path.addAll(moveDiagonal(first, movement));
        return path;
    }

    private Position moveStraightStep(Position source, Movement movement) {
        if (movement.isRowBiggerThanCol()) {
            return source.moveRow(movement.calculateRowDirection());
        }
        return source.moveCol(movement.calculateColDirection());
    }

    private List<Position> moveDiagonal(Position source, Movement movement) {
        List<Position> path = new ArrayList<>();
        int rowDirection = movement.calculateRowDirection();
        int colDirection = movement.calculateColDirection();

        for (int i = 0; i < DIAGONAL_DISTANCE; i++) {
            source = source.moveDiagonal(rowDirection, colDirection);
            path.add(source);
        }
        return path;
    }

    private void validatePath(List<Position> path, BoardChecker board) {
        if (isNotEmptyPath(path, board)) {
            throw new IllegalArgumentException(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }
    }

    private boolean isNotEmptyPath(List<Position> path, BoardChecker board) {
        return path.stream()
                .anyMatch(board::hasPieceAt);
    }
}
