package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.piece.PieceVO;
import janggi.domain.board.Position;
import java.util.ArrayList;
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
        Path path = new Path();
        Position next = current;
        try {
            while (true) {
                next = baseDir.move(next);
                path.makePath(next);
            }
        } catch (IllegalArgumentException e) {
            paths.addPath(path);
        }
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, PieceVO> boardState, PieceVO movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateSlidePath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateSlidePath(Path route, Map<Position, PieceVO> state, List<Position> dests, PieceVO me) {
        for (Position pos : route) {
            if (processAndCheckBlocked(pos, state, dests, me)) break;
        }
    }

    private boolean processAndCheckBlocked(Position pos, Map<Position, PieceVO> state, List<Position> dests, PieceVO me) {
        PieceVO target = state.get(pos);
        if (target == null || !target.isSameSide(me)) {
            dests.add(pos);
        }
        return target != null;
    }
}
