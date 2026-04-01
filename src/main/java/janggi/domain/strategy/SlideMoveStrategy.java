package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Path;

public class SlideMoveStrategy extends PieceStrategy {

    @Override
    protected Path navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        Path path = new Path();
        Position next = current;
        while (baseDir.canMove(next) && boardInfo.isEmpty(baseDir.move(next))) {
            next = baseDir.move(next);
            path.makePath(next);
        }
        if (!baseDir.canMove(next)) {
            return path;
        }
        return navigationIfEnemy(current, baseDir, boardInfo, next, path);
    }

    private Path navigationIfEnemy(Position current, Direction baseDir, BoardInfo boardInfo, Position next,
                                   Path path) {
        next = baseDir.move(next);
        if (boardInfo.isAlly(current, next)) {
            return path;
        }
        path.makePath(next);
        return path;
    }
}
