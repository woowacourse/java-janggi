package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;

public class StepMoveStrategy extends PieceStrategy {

    @Override
    protected void navigationPath(Position current, Direction baseDir, Paths paths) {
        if (baseDir.canMove(current)) {
            Path path = new Path();
            path.makePath(baseDir.move(current));
            paths.addPath(path);
        }
    }

    @Override
    protected boolean isAppendable(Position currentPosition, Position targetPosition, BoardInfo boardInfo) {
        if (boardInfo.isEmpty(targetPosition)) {
            return true;
        }
        return !boardInfo.isAlly(currentPosition, targetPosition);
    }
}
