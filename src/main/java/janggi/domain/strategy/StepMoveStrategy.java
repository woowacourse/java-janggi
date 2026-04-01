package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
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
        if (baseDir.canMove(current)) {
            Path path = new Path();
            path.makePath(baseDir.move(current));
            paths.addPath(path);
        }
    }

    @Override
    public List<Position> destinationsOf(Position currentPosition, BoardInfo boardInfo) {
        List<Position> destinations = new ArrayList<>();
        for (Path route : routes) {
            validateStepPath(route, boardState, destinations, movingPiece);
        }
        return destinations;
    }

    private void validateStepPath(Path route, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Position dest = route.iterator().next();
        Piece target = state.get(dest);

        if (target == null || !target.isAlly(me)) {
            dests.add(dest);
        }
    }
}
