package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
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
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateSlidePath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateSlidePath(Path route, Map<Position, Piece> state, List<Position> dests, Piece me) {
        for (Position pos : route) {
            if (processAndCheckBlocked(pos, state, dests, me)) {
                break;
            }
        }
    }

    private boolean processAndCheckBlocked(Position pos, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Piece target = state.get(pos);
        if (target == null || !target.isSameSide(me)) {
            dests.add(pos);
        }
        return target != null;
    }
}
