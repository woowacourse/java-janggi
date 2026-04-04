package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class HorseStrategy implements MoveStrategy {

    private static final int STRAIGHT_DISTANCE = 1;
    private static final int DIAGONAL_DISTANCE = 1;

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceStrategy pieceStrategy) {
        DirectionInformation direction = new DirectionInformation(source, destination);
        validateDistance(direction);
        List<Position> path = findPath(source, direction);
        List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
        validatePath(pathBeforeDestination, board);
    }

    private void validateDistance(DirectionInformation direction) {
        if (direction.isInvalidMoveDistance(DIAGONAL_DISTANCE, STRAIGHT_DISTANCE + DIAGONAL_DISTANCE)) {
            throw new IllegalArgumentException(
                    ExceptionMessage.INVALID_DIAGONAL_STEP_MOVE.getMessage(STRAIGHT_DISTANCE, DIAGONAL_DISTANCE)
            );
        }
    }

    private List<Position> findPath(Position source, DirectionInformation direction) {
        List<Position> path = new ArrayList<>();

        Position first = moveStraightStep(source, direction);
        path.add(first);
        path.addAll(moveDiagonal(first, direction));
        return path;
    }

    private Position moveStraightStep(Position source, DirectionInformation direction) {
        if (direction.isRowBiggerThanCol()) {
            return source.moveRow(direction.calculateRowDirection());
        }
        return source.moveCol(direction.calculateColDirection());
    }

    private List<Position> moveDiagonal(Position source, DirectionInformation direction) {
        List<Position> path = new ArrayList<>();
        int rowDirection = direction.calculateRowDirection();
        int colDirection = direction.calculateColDirection();

        for (int i = 0; i < DIAGONAL_DISTANCE; i++) {
            source = source.moveDiagonal(rowDirection, colDirection);
            path.add(source);
        }
        return path;
    }

    private void validatePath(List<Position> path, BoardChecker board) {
        if (isEmptyPath(path, board)) {
            throw new IllegalArgumentException(ExceptionMessage.PATH_NOT_EMPTY.getMessage());
        }
    }

    private boolean isEmptyPath(List<Position> path, BoardChecker board) {
        return path.stream()
                .anyMatch(board::hasPieceAt);
    }
}
