package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addCannonPath(current, baseDir, paths);
        }
        return paths;
    }

    private void addCannonPath(Position current, Direction baseDir, Paths paths) {
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
            validateCannonPath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateCannonPath(Path route, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Iterator<Position> it = route.iterator();
        if (findBridge(it, state)) {
            findDestinationsAfterJump(it, state, dests, me);
        }
    }

    private boolean findBridge(Iterator<Position> it, Map<Position, Piece> state) {
        Piece target = null;
        while (it.hasNext() && (target = state.get(it.next())) == null) {
        }
        return target != null && !target.isCannon();
    }

    private void findDestinationsAfterJump(Iterator<Position> it, Map<Position, Piece> state, List<Position> dests, Piece me) {
        while (it.hasNext() && !processTarget(it.next(), state, dests, me)) {
        }
    }

    private boolean processTarget(Position pos, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Piece target = state.get(pos);
        if (target == null) {
            dests.add(pos);
            return false;
        }
        addIfCapturable(pos, target, dests, me);
        return true;
    }

    private void addIfCapturable(Position pos, Piece target, List<Position> dests, Piece me) {
        if (!target.isCannon() && !target.isSameSide(me)) {
            dests.add(pos);
        }
    }
}
