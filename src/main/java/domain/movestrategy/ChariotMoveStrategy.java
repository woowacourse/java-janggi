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

        for (Direction direction : Direction.ORTHOGONAL_DIRECTIONS) {
            collectMovablePositions(board, from, direction, movable);
        }

        for (Direction direction : Direction.DIAGONAL_DIRECTIONS) {
            collectDiagonalMovablePositions(board, from, direction, movable);
        }

        return movable;
    }

    private void collectMovablePositions(
            final Board board,
            final Position from,
            final Direction direction,
            final List<Position> movable
    ) {
        Position current = from.move(direction);

        while (current.isInsideBoard() && board.isEmpty(current)) {
            movable.add(current);
            current = current.move(direction);
        }

        if (current.isInsideBoard() && board.isOpposite(from, current)) {
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

        while (current.isInsideBoard()
                && previous.isDiagonalConnected(current)
                && board.isEmpty(current)
        ) {
            movable.add(current);
            previous = current;
            current = current.move(direction);
        }

        if (current.isInsideBoard()
                && previous.isDiagonalConnected(current)
                && board.isOpposite(from, current)) {
            movable.add(current);
        }
    }
}
