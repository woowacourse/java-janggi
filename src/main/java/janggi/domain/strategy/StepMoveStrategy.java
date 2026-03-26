package janggi.domain.strategy;

import janggi.domain.Direction;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.PieceVO;
import janggi.domain.Position;
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
        path.makePath(baseDir.move(current));
        paths.addPath(path);
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState, PieceVO movingPieceVO) {
        return null;
    }
}
