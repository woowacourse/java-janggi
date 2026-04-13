package domain.movement;

import domain.movement.exception.EmptyDirectionSequenceResultException;
import domain.movement.exception.MovementErrorMessage;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class DirectionSequenceResult {
    private final List<Position> positions;

    public DirectionSequenceResult(List<Position> positions) {
        this.positions = List.copyOf(positions);
    }

    public Position lastPosition() {
        if (positions.isEmpty()) {
            throw new EmptyDirectionSequenceResultException(MovementErrorMessage.EMPTY_DIRECTION_SEQUENCE_RESULT);
        }
        return positions.getLast();
    }

    public List<Position> pathPositions() {
        return new ArrayList<>(positions.subList(0, positions.size() - 1));
    }
}
