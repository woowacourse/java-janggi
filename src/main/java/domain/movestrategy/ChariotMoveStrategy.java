package domain.movestrategy;

import domain.board.Board;
import domain.board.Direction;
import domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        List<Position> movable = new ArrayList<>();
        collectLinePositions(board, from, movable);
        collectDiagonalPositions(board, from, movable);

        return movable;
    }

    private void collectLinePositions(final Board board, final Position from, final List<Position> movable) {
        for (Direction direction : Direction.ORTHOGONAL_DIRECTIONS) {
            collectMovablePositions(board, from, direction, movable);
        }
    }

    private void collectDiagonalPositions(final Board board, final Position from, final List<Position> movable) {
        for (Direction direction : Direction.DIAGONAL_DIRECTIONS) {
            collectDiagonalMovablePositions(board, from, direction, movable);
        }
    }

    private void collectMovablePositions(
            final Board board,
            final Position from,
            final Direction direction,
            final List<Position> movable
    ) {
        Position current = from.move(direction);

        while (canMove(current, board)) {
            movable.add(current);
            current = current.move(direction);
        }

        if (canCapture(board, from, current)) {
            movable.add(current);
        }
    }

    private void collectDiagonalMovablePositions(
            final Board board,
            final Position from,
            final Direction direction,
            final List<Position> movable
    ) {
        Position previous = from;
        Position current = from.move(direction);

        while (canMoveDiagonal(previous, current, board)) {
            movable.add(current);
            previous = current;
            current = current.move(direction);
        }

        if (canCaptureDiagonal(board, from, previous, current)) {
            movable.add(current);
        }
    }

    private boolean canMove(final Position current, final Board board) {
        return current.isInsideBoard() && board.isEmpty(current);
    }

    private boolean canCapture(final Board board, final Position from, final Position current) {
        return current.isInsideBoard() && board.isOpposite(from, current);
    }

    private boolean canMoveDiagonal(final Position previous, final Position current, final Board board) {
        return previous.isDiagonalConnected(current) && canMove(current, board);
    }

    private boolean canCaptureDiagonal(
            final Board board,
            final Position from,
            final Position previous,
            final Position current
    ) {
        return previous.isDiagonalConnected(current) && canCapture(board, from, current);
    }
}
