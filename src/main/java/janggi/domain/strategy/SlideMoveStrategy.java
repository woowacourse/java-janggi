package janggi.domain.strategy;

import janggi.domain.Direction;
import janggi.domain.Path;
import janggi.domain.Paths;
import janggi.domain.PieceVO;
import janggi.domain.Position;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class SlideMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addSlidePath(current, baseDir, paths);
        }
        return paths;
    }

    private void addSlidePath(Position current, Direction baseDir, Paths paths) {
        try {
            Path path = new Path();
            Position next = baseDir.move(current);
            path.makePath(next);
            paths.addPath(path);
            addSlidePath(next, baseDir, paths);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState, PieceVO movingPieceVO) {
        return null;
    }
}
