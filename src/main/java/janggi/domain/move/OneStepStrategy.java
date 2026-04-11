package janggi.domain.move;

import janggi.domain.board.BoardReader;
import janggi.domain.space.Direction;
import janggi.domain.space.Path;
import janggi.domain.space.Position;
import java.util.ArrayList;
import java.util.List;

public class OneStepStrategy implements MovementStrategy {
    private final List<Direction> defaultDirections;

    public OneStepStrategy(List<Direction> defaultDirections) {
        this.defaultDirections = defaultDirections;
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board) {
        return generatePaths(current, board).stream()
                .map(Path::getDestination)
                .toList();
    }

    @Override
    public List<Path> generatePaths(Position current, BoardReader board) {
        List<Direction> movableDirections = new ArrayList<>(defaultDirections);
        movableDirections.addAll(board.getPalaceDiagonals(current));

        return movableDirections.stream()
                .filter(current::canMove)
                .map(direction -> Path.ofOneStep(current, direction))
                .toList();
    }
}
