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
        for (Direction baseDirection : baseDirections) {
            addSlidePath(current, baseDirection, paths);
        }
        return paths;
    }

    private void addSlidePath(Position current, Direction baseDirection, Paths paths) {
        Path path = new Path();
        Position pointer = current;

        while (pointer.canMove(baseDirection)) {
            pointer = baseDirection.move(pointer);
            path.add(pointer);
        }

        paths.addPath(path);
    }

    @Override
    public List<Position> determineDestinations(Paths routes, Map<Position, Piece> boardState, Piece movingPiece) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateSlidePath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateSlidePath(Path route, Map<Position, Piece> state, List<Position> destinations, Piece me) {
        for (Position pos : route) {
            if (processAndCheckBlocked(pos, state, destinations, me)) {
                break;
            }
        }
    }

    private boolean processAndCheckBlocked(Position pos, Map<Position, Piece> state, List<Position> destinations,
                                           Piece me) {
        Piece target = state.get(pos);
        if (target == null || !target.isSameSide(me)) {
            destinations.add(pos);
        }
        return target != null;
    }
}
