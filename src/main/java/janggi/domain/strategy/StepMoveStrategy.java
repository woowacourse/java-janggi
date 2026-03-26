package janggi.domain.strategy;

import janggi.domain.Direction;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.PieceVO;
import janggi.domain.Position;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class StepMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addStepPath(current, baseDir, paths);
        }
        return paths;
    }

    private void addStepPath(Position current, Direction baseDir, Paths paths) {
        Path path = new Path();
        try {
            path.makePath(baseDir.move(current));
            paths.addPath(path);
        } catch (IllegalArgumentException exception) {

        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState, PieceVO movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateStepPath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateStepPath(Path route, Map<Position, PieceVO> state, List<Position> dests, PieceVO me) {
        Position dest = route.iterator().next();
        PieceVO target = state.get(dest);

        if (target == null || target.isSameSide(me)) {
            dests.add(dest);
        }
    }
}
