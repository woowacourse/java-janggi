package strategy.move;

import domain.Direction;
import domain.MovePath;
import domain.TeamColor;
import java.util.ArrayList;
import java.util.List;

public class RookMoveStrategy extends MoveStrategy {

    @Override
    public List<MovePath> getPaths(TeamColor teamColor) {
        List<MovePath> paths = new ArrayList<>();

        addStraightPaths(paths, Direction.NORTH);
        addStraightPaths(paths, Direction.SOUTH);
        addStraightPaths(paths, Direction.EAST);
        addStraightPaths(paths, Direction.WEST);

        return paths;
    }

    private void addStraightPaths(List<MovePath> paths, Direction direction) {
        for (int distance = 1; distance <= 9; distance++) {
            List<Direction> steps = new ArrayList<>();
            for (int i = 0; i < distance; i++) {
                steps.add(direction);
            }
            paths.add(new MovePath(steps));
        }
    }

}
