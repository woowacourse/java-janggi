package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Path;

public class StepMoveStrategy extends PieceStrategy {

    @Override
    protected Path navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        Path path = new Path();
        if (baseDir.canMove(current)) {
            path.makePath(baseDir.move(current));
        }
        return path;
    }

    @Override
    protected boolean isAppendable(Position currentPosition, Position targetPosition, BoardInfo boardInfo) {
        if (boardInfo.isEmpty(targetPosition)) {
            return true;
        }
        return !boardInfo.isAlly(currentPosition, targetPosition);
    }
}
