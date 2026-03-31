package domain.movestrategy;

import domain.board.Board;
import domain.piece.Delta;
import domain.piece.Position;
import java.util.List;

public class GuardMoveStrategy implements MoveStrategy {

    private static final List<Delta> ALL_DIRECTIONS = List.of(
            Delta.UP, Delta.RIGHT_UP, Delta.RIGHT, Delta.RIGHT_DOWN,
            Delta.DOWN, Delta.LEFT_DOWN, Delta.LEFT, Delta.LEFT_UP
    );

    @Override
    public List<Position> calculateMovablePositions(final Position from, final Board board) {
        return ALL_DIRECTIONS.stream()
                .map(from::move)
                .filter(board::inBoard)
                .filter(to -> !isAlly(from, to, board))
                .toList();
    }
}
