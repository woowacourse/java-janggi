package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Direction;
import java.util.List;

public class GuardMoveStrategy implements MoveStrategy {

    private static final List<Direction> BASIC_DIRECTIONS = Direction.ORTHOGONAL_DIRECTIONS

    private static final List<Direction> DIRECTION_INSIDE_PALACE = List.of(
            Direction.UP, Direction.RIGHT_UP, Direction.RIGHT, Direction.RIGHT_DOWN,
            Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT, Direction.LEFT_UP
    );

    @Override
    public List<Position> getMovablePositions(final Board board, final Position from) {
        return DIRECTION_INSIDE_PALACE.stream()
                .map(from::move)
                .filter(Position::isInside)
                .filter(position -> board.isEmptyOrOpposite(from, position))
                .toList();
    }
}
