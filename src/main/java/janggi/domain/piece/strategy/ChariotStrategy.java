package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.camp.CampType;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class ChariotStrategy extends PalaceStrategy implements MoveStrategy {

    @Override
    public void validate(Position source, Position destination, CampType campType, BoardChecker board, PieceRule pieceRule) {
        Movement movement = new Movement(source, destination);
        if (isPalaceRange(source, destination)) {
            List<Position> path = findPathInPalace(source, movement);
            List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
            validatePath(pathBeforeDestination, board);
            return;
        }
        List<Position> path = findPath(source, movement);
        List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
        validatePath(pathBeforeDestination, board);
    }

    private List<Position> findPathInPalace(Position source, Movement movement) {
        if (movement.isHorizontal()) {
            return createPath(source, movement.colDistance(), Position::moveCol);
        }
        if (movement.isVertical()) {
            return createPath(source, movement.rowDistance(), Position::moveRow);
        }
        if (movement.isDiagonal()) {
            List<Position> path = new ArrayList<>();
            int colDirection = Integer.signum(movement.colDistance());
            int rowDirection = Integer.signum(movement.rowDistance());
            for (int i = 0; i < Math.abs(2); i++) {
                source = source.moveDiagonal(rowDirection, colDirection);
                path.add(source);
            }
            return path;
        }
        throw new IllegalArgumentException(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    private List<Position> findPath(Position source, Movement movement) {
        if (movement.isHorizontal()) {
            return createPath(source, movement.colDistance(), Position::moveCol);
        }
        if (movement.isVertical()) {
            return createPath(source, movement.rowDistance(), Position::moveRow);
        }
        throw new IllegalArgumentException(ExceptionMessage.ONLY_STRAIGHT_MOVE_ALLOWED.getMessage());
    }

    private List<Position> createPath(Position source, int difference, BiFunction<Position, Integer, Position> move) {
        List<Position> path = new ArrayList<>();
        int direction = Integer.signum(difference);
        int distance = Math.abs(difference);

        for (int i = 0; i < distance; i++) {
            source = move.apply(source, direction);
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
