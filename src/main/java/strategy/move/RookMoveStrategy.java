package strategy.move;

import domain.board.Direction;
import domain.board.MovePath;
import domain.piece.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class RookMoveStrategy implements MoveStrategy {
    private static final int MIN_DISTANCE = 1;
    private static final int MAX_STRAIGHT_DISTANCE = 9;

    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        final List<MovePath> paths = new ArrayList<>();

        addStraightPaths(paths, Direction.NORTH);
        addStraightPaths(paths, Direction.SOUTH);
        addStraightPaths(paths, Direction.EAST);
        addStraightPaths(paths, Direction.WEST);

        return paths;
    }

    private void addStraightPaths(List<MovePath> paths, Direction direction) {
        for (int distance = MIN_DISTANCE; distance <= MAX_STRAIGHT_DISTANCE; distance++) {
            final List<Direction> steps = new ArrayList<>();
            for (int i = 0; i < distance; i++) {
                steps.add(direction);
            }
            paths.add(new MovePath(steps));
        }
    }

}
