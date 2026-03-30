package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public class HorseMoveStrategy implements MoveStrategy {

    public static final List<List<Direction>> HORSE_MOVE_SEQUENCES = List.of(
            List.of(Direction.TOP, Direction.LEFT_TOP),
            List.of(Direction.TOP, Direction.RIGHT_TOP),

            List.of(Direction.DOWN, Direction.LEFT_DOWN),
            List.of(Direction.DOWN, Direction.RIGHT_DOWN),

            List.of(Direction.LEFT, Direction.LEFT_TOP),
            List.of(Direction.LEFT, Direction.LEFT_DOWN),

            List.of(Direction.RIGHT, Direction.RIGHT_TOP),
            List.of(Direction.RIGHT, Direction.RIGHT_DOWN)
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        if (board.isSameSide(from, to)) {
            return false;
        }

        return HORSE_MOVE_SEQUENCES.stream()
                .anyMatch(seq -> canFollowSequence(board, from, to, seq));
    }

    private boolean canFollowSequence(BoardView board, Position from, Position to, List<Direction> sequence) {
        Direction firstStep = sequence.get(0);
        Direction secondStep = sequence.get(1);

        return from.moveIfInBounds(firstStep)
                .filter(board::isEmpty)
                .flatMap(firstPos -> firstPos.moveIfInBounds(secondStep))
                .filter(to::equals)
                .isPresent();
    }
}