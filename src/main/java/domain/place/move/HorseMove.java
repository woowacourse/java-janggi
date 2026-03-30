package domain.place.move;

import domain.place.Place;
import domain.place.moveStrategy.Direction;
import domain.position.Position;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class HorseMove implements Move {

    private static final List<List<Direction>> HORSE_MOVE_SEQUENCES = List.of(
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
    public List<Position> getPath(Position from) {
        return HORSE_MOVE_SEQUENCES.stream()
                .flatMap(sequence -> getTargetPositionIfPathClear(from, sequence).stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean canMove(Map<Position, Place> path, Position from, Position to) {
        return HORSE_MOVE_SEQUENCES.stream()
                .anyMatch(sequence -> canFollowSequence(path, from, to, sequence));
    }

    private Optional<Position> getTargetPositionIfPathClear(Position from, List<Direction> sequence) {
        Direction firstStepDirection = sequence.get(0);
        Direction secondStepDirection = sequence.get(1);

        return from.moveIfInBounds(firstStepDirection)
                .flatMap(firstStepPosition -> firstStepPosition.moveIfInBounds(secondStepDirection));
    }

    private boolean canFollowSequence(Map<Position, Place> path, Position from, Position to, List<Direction> sequence) {
        Direction firstStepDirection = sequence.get(0);
        Direction secondStepDirection = sequence.get(1);

        return from.moveIfInBounds(firstStepDirection)
                .filter(firstStepPosition -> !path.containsKey(firstStepPosition))
                .flatMap(firstStepPosition -> firstStepPosition.moveIfInBounds(secondStepDirection))
                .filter(to::equals)
                .isPresent();
    }
}