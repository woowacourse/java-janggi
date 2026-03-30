package domain.place.moveStrategy;

import domain.board.BoardView;
import domain.position.Position;
import java.util.List;

public class ElephantMoveStrategy implements MoveStrategy {

    public static final List<List<Direction>> ELEPHANT_MOVE_SEQUENCES = List.of(
            List.of(Direction.TOP, Direction.LEFT_TOP, Direction.LEFT_TOP),
            List.of(Direction.TOP, Direction.RIGHT_TOP, Direction.RIGHT_TOP),
            List.of(Direction.DOWN, Direction.LEFT_DOWN, Direction.LEFT_DOWN),
            List.of(Direction.DOWN, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN),
            List.of(Direction.LEFT, Direction.LEFT_TOP, Direction.LEFT_TOP),
            List.of(Direction.LEFT, Direction.LEFT_DOWN, Direction.LEFT_DOWN),
            List.of(Direction.RIGHT, Direction.RIGHT_TOP, Direction.RIGHT_TOP),
            List.of(Direction.RIGHT, Direction.RIGHT_DOWN, Direction.RIGHT_DOWN)
    );

    @Override
    public boolean canMove(BoardView board, Position from, Position to) {
        if (board.isSameSide(from, to)) {
            return false;
        }

        return ELEPHANT_MOVE_SEQUENCES.stream()
                .anyMatch(sequence -> canFollowSequence(board, from, to, sequence));
    }

    private boolean canFollowSequence(BoardView board, Position from, Position to, List<Direction> sequence) {
        Direction firstStepDirection = sequence.get(0);
        Direction secondStepDirection = sequence.get(1);
        Direction thirdStepDirection = sequence.get(2);

        return from.moveIfInBounds(firstStepDirection)
                .filter(board::isEmpty)
                .flatMap(firstStepPosition -> firstStepPosition.moveIfInBounds(secondStepDirection))
                .filter(board::isEmpty)
                .flatMap(secondStepPosition -> secondStepPosition.moveIfInBounds(thirdStepDirection))
                .filter(to::equals)
                .isPresent();
    }
}