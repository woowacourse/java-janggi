package janggi.domain.move;

import janggi.domain.Side;
import janggi.domain.board.BoardReader;
import janggi.domain.space.Direction;
import janggi.domain.space.Path;
import janggi.domain.space.Position;
import java.util.ArrayList;
import java.util.List;

public class ForwardStepStrategy implements MovementStrategy {
    private final Side side;
    private final List<Direction> defaultDirections;

    public ForwardStepStrategy(Side side, List<Direction> directions) {
        this.side = side;
        this.defaultDirections = directions;
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
                .map(dir -> Path.ofOneStep(current, dir))
                .filter(path -> isForward(current, path.getDestination()))
                .toList();
    }

    private boolean isForward(Position current, Position target) {
        if (side == Side.CHO) {
            return target.getY() >= current.getY();
        }
        return target.getY() <= current.getY();
    }
}
