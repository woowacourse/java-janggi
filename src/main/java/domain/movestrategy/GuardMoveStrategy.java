package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import domain.piece.Delta;
import java.util.List;

public class GuardMoveStrategy extends BasicMoveStrategy {

    private static final List<Delta> ALL_DIRECTIONS = List.of(
            Delta.UP, Delta.RIGHT_UP, Delta.RIGHT, Delta.RIGHT_DOWN,
            Delta.DOWN, Delta.LEFT_DOWN, Delta.LEFT, Delta.LEFT_UP
    );

    @Override
    public List<Position> getMovablePositions(Board board, Position from) {
        return ALL_DIRECTIONS.stream()
                .map(from::move)
                .filter(Position::isInside)
                .filter(position -> board.isEmptyOrOpposite(from, position))
                .toList();
    }
}
