package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class CannonStrategy extends PalaceStrategy {

    private static final long REQUIRED_PIECE_COUNT = 1;

    @Override
    protected void validatePalaceMove(Position source, Position destination, Movement movement, BoardChecker board) {
        List<Position> path = findPathInPalace(source, movement);
        List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
        validatePath(source, pathBeforeDestination, destination, board);
    }

    @Override
    protected void validateNormalMove(Position source, Position destination, Movement movement, BoardChecker board) {
        List<Position> path = findPath(source, movement);
        List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
        validatePath(source, pathBeforeDestination, destination, board);
    }

    private List<Position> findPathInPalace(Position source, Movement movement) {
        if (movement.isStraight()) {
            return findPath(source, movement);
        }
        if (isPalaceDiagonalPath(source, source.moveDiagonal(movement.rowDistance(), movement.colDistance()), movement)) {
            return createDiagonalPath(source, movement);
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

    private List<Position> createPath(Position source, int distance, BiFunction<Position, Integer, Position> move) {
        List<Position> path = new ArrayList<>();
        int direction = Integer.signum(distance);

        for (int i = 0; i < Math.abs(distance); i++) {
            source = move.apply(source, direction);
            path.add(source);
        }
        return path;
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

    private void validatePath(Position source, List<Position> path, Position destination, BoardChecker board) {
        validateJumpedPieceCount(path, board);
        validateDifferentPieceRule(source, path, board);
        validateDestination(source, destination, board);
    }

    private void validateJumpedPieceCount(List<Position> path, BoardChecker board) {
        long jumpedPieceCount = countJumpedPiece(path, board);

        if (jumpedPieceCount != REQUIRED_PIECE_COUNT) {
            throw new IllegalArgumentException(
                    ExceptionMessage.INVALID_JUMPED_PIECE_COUNT.getMessage(REQUIRED_PIECE_COUNT)
            );
        }
    }

    private long countJumpedPiece(List<Position> path, BoardChecker board) {
        return path.stream()
                .filter(board::hasPieceAt)
                .count();
    }

    private void validateDifferentPieceRule(Position source, List<Position> path, BoardChecker board) {
        if (hasSamePieceType(source, path, board)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
        }
    }

    private boolean hasSamePieceType(Position source, List<Position> path, BoardChecker board) {
        return path.stream()
                .anyMatch(position -> board.isSamePieceRule(source, position));
    }

    private void validateDestination(Position source, Position destination, BoardChecker board) {
        if (board.isSamePieceRule(source, destination)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
        }
    }
}
