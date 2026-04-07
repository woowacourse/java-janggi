package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Direction;
import java.util.ArrayList;
import java.util.List;

public class ChariotMoveStrategy implements MoveStrategy {

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        List<Position> movable = new ArrayList<>();

        for (Direction direction : Direction.ORTHOGONAL_DIRECTIONS) {
            collectMovablePositions(board, from, direction, movable);
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

        while (current.isInside() && board.isEmpty(current)) {
            movable.add(current);
            current = current.move(direction);
        }

        if (current.isInside() && board.isOpposite(from, current)) {
            movable.add(current);
        }
    }
}
