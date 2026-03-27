package janggi.domain.strategy;

import janggi.domain.board.Direction;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import janggi.domain.piece.Piece;
import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addHorsePaths(current, baseDir, paths);
        }
        return paths;
    }

    private void addHorsePaths(Position current, Direction baseDir, Paths paths) {
        for (Direction diagonalDir : baseDir.getAdjacentDiagonals()) {
            createAndAddSequence(current, paths, baseDir, diagonalDir);
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
            validateHorsePath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateHorsePath(Path route, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Iterator<Position> it = route.iterator();
        Position transit = it.next(); // 멱 (1번째)

        if (state.get(transit) == null) {
            addIfValid(it.next(), state, dests, me); // 도착지 (2번째)
        }
    }

    private void addIfValid(Position dest, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Piece target = state.get(dest);
        if (target == null || !target.isSameSide(me)) {
            dests.add(dest);
        }
    }
}
