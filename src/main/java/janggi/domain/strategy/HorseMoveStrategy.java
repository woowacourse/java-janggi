package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy extends PieceStrategy {

    @Override
    public Paths findMovablePaths(Position current, EnumSet<Direction> baseDirections, BoardInfo boardInfo) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            addHorsePaths(current, baseDir, paths);
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

    private void addHorsePaths(Position current, Direction baseDir, Paths paths) {
        for (Direction diagonalDir : baseDir.nextDiagonalDirections()) {
            createAndAddSequence(current, paths, baseDir, diagonalDir);
        }
    }

    private void createAndAddSequence(Position start, Paths paths, Direction... directions) {
        Path path = new Path();
        Position current = start;

        for (Direction direction : directions) {
            current = createSequenceIfPossible(current, direction, path);
            if (current == null) {
                return;
            }
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

    private void addIfValid(Position dest, Map<Position, Piece> state, List<Position> dests, Piece me) {
        Piece target = state.get(dest);
        if (target == null || !target.isAlly(me)) {
            dests.add(dest);
        }
    }
}
