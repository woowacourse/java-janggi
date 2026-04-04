package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class CannonStrategy implements MoveStrategy {

    private static final long REQUIRED_PIECE_COUNT = 1;

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceStrategy pieceStrategy) {
        Movement movement = new Movement(source, destination);
        List<Position> path = findPath(source, movement);
        List<Position> pathBeforeDestination = path.subList(0, path.size() - 1);
        validatePath(pathBeforeDestination, destination, board, pieceStrategy);
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

    private void validatePath(List<Position> path, Position destination, BoardChecker board, PieceStrategy pieceStrategy) {
        validateJumpedPieceCount(path, board);
        validateDifferentPieceRule(path, board, pieceStrategy);
        validateDestination(board, pieceStrategy, destination);
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

    private void validateDifferentPieceRule(List<Position> path, BoardChecker board, PieceStrategy pieceStrategy) {
        if (hasSamePieceType(path, board, pieceStrategy)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_IN_PATH.getMessage());
        }
    }

    private boolean hasSamePieceType(List<Position> path, BoardChecker board, PieceStrategy pieceStrategy) {
        return path.stream()
                .anyMatch(position -> board.hasSamePieceRuleAt(position, pieceStrategy));
    }

    private void validateDestination(BoardChecker board, PieceStrategy pieceStrategy, Position position) {
        if (board.hasSamePieceRuleAt(position, pieceStrategy)) {
            throw new IllegalArgumentException(ExceptionMessage.SAME_PIECE_TYPE_AT_DESTINATION.getMessage());
        }
    }
}
