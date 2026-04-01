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

public class SlideMoveStrategy extends PieceStrategy {

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
        while (baseDir.canMove(next)) {
            next = baseDir.move(next);
            path.makePath(next);
        }
        paths.addPath(path);
    }

    @Override
    public List<Position> destinationsOf(Position currentPosition, BoardInfo boardInfo) {
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
        if (target == null || !target.isAlly(me)) {
            dests.add(pos);
        }
        return target != null;
    }
}
