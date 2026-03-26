package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import java.util.ArrayList;
import java.util.List;

public class StraightMovement implements Movement {
    private final BoardMediator boardMediator;
    private final int maxDistance;
    private final Direction direction;

    public StraightMovement(int maxDistance, Direction direction, BoardMediator boardMediator) {
        this.maxDistance = maxDistance;
        this.direction = direction;
        this.boardMediator = boardMediator;
    }

    @Override
    public boolean canReach(final Position from) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Position calculateDestination(final Position from) {
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                return to;
            }
        }
        return from.calculateNext(maxDistance, direction);
    }

    @Override
    public List<Position> calculateTraces(final Position from) {
        final List<Position> traces = new ArrayList<>();
        for (int distance = 1; distance <= maxDistance; distance++) {
            Position to = from.calculateNext(distance, direction);
            if (boardMediator.existsInPosition(to)) {
                break;
            }
            traces.add(to);
        }
        return traces;
    }
}
