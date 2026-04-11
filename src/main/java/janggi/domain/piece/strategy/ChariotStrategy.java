package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class ChariotStrategy implements MoveStrategy {

    @Override
    public void validate(Position source, Position destination, BoardChecker board) {
        Movement movement = new Movement(source, destination);
        List<Position> path = findPath(source, destination, movement, board);
        board.validateEmptyPath(path.subList(0, path.size() - 1));
    }

    private List<Position> findPath(Position source, Position destination, Movement movement, BoardChecker board) {
        if (board.isPalaceRange(source, destination) && board.isAllowedDiagonalPath(source, destination)) {
            return createDiagonalPath(source, movement);
        }
        return createStraightPath(source, movement);
    }

    private List<Position> createDiagonalPath(Position source, Movement movement) {
        List<Position> path = new ArrayList<>();
        int colDirection = Integer.signum(movement.colDistance());
        int rowDirection = Integer.signum(movement.rowDistance());
        int distance = Math.abs(movement.rowDistance());

        for (int i = 0; i < distance; i++) {
            source = source.moveDiagonal(rowDirection, colDirection);
            path.add(source);
        }
        return path;
    }

    private List<Position> createStraightPath(Position source, Movement movement) {
        if (movement.isHorizontal()) {
            return createPath(source, movement.colDistance(), Position::moveCol);
        }
        if (movement.isVertical()) {
            return createPath(source, movement.rowDistance(), Position::moveRow);
        }
        throw new IllegalArgumentException(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    private List<Position> createPath(Position source, int distance, BiFunction<Position, Integer, Position> move) {
        List<Position> path = new ArrayList<>();
        int direction = Integer.signum(distance);

        for (int i = 0; i < Math.abs(distance); i++) {
            source = move.apply(source, direction);
            path.add(source);
        }
        return path;
    }
}
