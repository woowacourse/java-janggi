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

    private void addCannonPath(Position current, Direction baseDirection, Paths paths) {
        Path path = new Path();
        Position next = current;

        while (next.canMove(baseDirection)) {
            next = baseDirection.move(next);
            path.makePath(next);
        }

        paths.addPath(path);
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateCannonPath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateCannonPath(Path route, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        Iterator<Position> it = route.iterator();
        if (findBridge(it, state)) {
            findDestinationsAfterJump(it, state, destinations, me);
        }
    }

    private boolean findBridge(Iterator<Position> it, Map<Position, Piece> state) {
        Piece firstPiece = findFirstPiece(it, state);
        return isValidBridge(firstPiece);
    }

    private Piece findFirstPiece(Iterator<Position> it, Map<Position, Piece> state) {
        while (it.hasNext()) {
            Piece piece = state.get(it.next());
            if (piece != null) {
                return piece;
            }
        }
        return null;
    }

    private boolean isValidBridge(Piece piece) {
        return (piece != null) && !piece.isCannon();
    }

    private void findDestinationsAfterJump(Iterator<Position> it, Map<Position, Piece> state, List<Position> dests, Piece me) {
        while (it.hasNext()) {
            Position position = it.next();
            Piece target = state.get(position);

            if (target == null) {
                dests.add(position);
                continue;
            }

            addTargetIfCapturable(position, target, dests, me);
            return;
        }
    }

    private void addTargetIfCapturable(Position position, Piece target, List<Position> destinations, Piece me) {
        if (!target.isCannon() && !target.isSameSide(me)) {
            destinations.add(position);
        }
    }
}
