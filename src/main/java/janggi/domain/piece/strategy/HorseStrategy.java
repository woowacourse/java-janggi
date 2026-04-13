package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    private static final int STRAIGHT_DISTANCE = 1;
    private static final int DIAGONAL_DISTANCE = 1;

    @Override
    public void validate(Position source, Position destination, BoardChecker board) {
        Movement movement = new Movement(source, destination);
        validateDistance(movement);
        List<Position> path = findPath(source, movement);
        List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
        board.validateEmptyPath(pathBeforeDestination);
    }

    private void validateDistance(Movement movement) {
        if (!movement.isValidMoveDistance(DIAGONAL_DISTANCE, STRAIGHT_DISTANCE + DIAGONAL_DISTANCE)) {
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
}
