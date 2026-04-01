package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CannonMoveStrategy extends PieceStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections, BoardInfo boardInfo) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addCannonPath(current, baseDir, paths);
        }
        return paths;
    }

    @Override
    protected Path navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        return null;
    }

    @Override
    protected boolean isAppendable(Position currentPosition, Position targetPosition, BoardInfo boardInfo) {
        return false;
    }

    private void addCannonPath(Position current, Direction baseDir, Paths paths) {
        Path path = new Path();
        Position next = current;
        while (baseDir.canMove(next)) {
            next = baseDir.move(next);
            path.makePath(next);
        }
        paths.addPath(path);
    }

    private boolean findBridge(Iterator<Position> it, Map<Position, Piece> state) {
        Piece target = null;
        while (it.hasNext() && (target = state.get(it.next())) == null) {
        }
        return target != null && !target.isCannon();
    }

    private void findDestinationsAfterJump(Iterator<Position> it, Map<Position, Piece> state, List<Position> dests,
                                           Piece movingPiece) {
        while (it.hasNext() && !processTarget(it.next(), state, dests, movingPiece)) {
        }
    }

    private boolean processTarget(Position pos, Map<Position, Piece> state, List<Position> dests, Piece movingPiece) {
        Piece target = state.get(pos);
        if (target == null) {
            dests.add(pos);
            return false;
        }
        addIfCapturable(pos, target, dests, movingPiece);
        return true;
    }

    private void addIfCapturable(Position pos, Piece target, List<Position> dests, Piece movingPiece) {
        if (!target.isCannon() && !target.isAlly(movingPiece)) {
            dests.add(pos);
        }
    }
}
