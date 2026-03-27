package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.piece.Piece;
import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addElephantPaths(current, paths, baseDir);
        }
        return paths;
    }

    private void addElephantPaths(Position current, Paths paths, Direction baseDir) {
        for (Direction diagonalDir : baseDir.getAdjacentDiagonals()) {
            createAndAddSequence(current, paths, baseDir, diagonalDir, diagonalDir);
        }
    }

    private void createAndAddSequence(Position start, Paths paths, Direction... directions) {
        Path path = new Path();
        Position current = start;

        for (Direction direction : directions) {
            current = createSequenceIfPossible(current, direction, path);
            if (current == null) return;
        }
        paths.addPath(path);
    }

    private Position createSequenceIfPossible(Position now, Direction direction, Path path) {
        if (!direction.canMove(now)) {
            return null;
        }
        Position next = direction.move(now);
        path.makePath(next);
        return next;
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateElephantPath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateElephantPath(Path route, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Iterator<Position> it = route.iterator();
        Position transit1 = it.next();
        Position transit2 = it.next();

        if (state.get(transit1) == null && state.get(transit2) == null) {
            addIfValid(it.next(), state, dests, me); // 최종 도착지
        }
    }

    private void addIfValid(Position dest, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Piece target = state.get(dest);
        if (target == null || !target.isSameSide(me)) {
            dests.add(dest);
        }
    }
}
