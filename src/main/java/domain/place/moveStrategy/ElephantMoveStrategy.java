package domain.place.moveStrategy;

import domain.place.Place;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Position> getPath(Position from) {
        return ELEPHANT_MOVE_SEQUENCES.stream()
                .flatMap(sequence -> getTargetPositionIfPathClear(from, sequence).stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean canMove(Map<Position, Place> path, Position from, Position to) {
        return ELEPHANT_MOVE_SEQUENCES.stream()
                .anyMatch(sequence -> canFollowSequence(path, from, to, sequence));
    }

    private Optional<Position> getTargetPositionIfPathClear(Position from, List<Direction> sequence) {
        Direction firstStepDirection = sequence.get(0);
        Direction secondStepDirection = sequence.get(1);
        Direction thirdStepDirection = sequence.get(2);

        return from.moveIfInBounds(firstStepDirection)
                .flatMap(firstStepPosition -> firstStepPosition.moveIfInBounds(secondStepDirection))
                .flatMap(secondStepPosition -> secondStepPosition.moveIfInBounds(thirdStepDirection));
    }

    private boolean canFollowSequence(Map<Position, Place> path, Position from, Position to, List<Direction> sequence) {
        Direction firstStepDirection = sequence.get(0);
        Direction secondStepDirection = sequence.get(1);
        Direction thirdStepDirection = sequence.get(2);

        return from.moveIfInBounds(firstStepDirection)
                .filter(firstStepPosition -> !path.containsKey(firstStepPosition)) // 첫 번째 멱 확인
                .flatMap(firstStepPosition -> firstStepPosition.moveIfInBounds(secondStepDirection))
                .filter(secondStepPosition -> !path.containsKey(secondStepPosition)) // 두 번째 멱 확인
                .flatMap(secondStepPosition -> secondStepPosition.moveIfInBounds(thirdStepDirection))
                .filter(to::equals)
                .isPresent();
    }
}