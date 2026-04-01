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
        return navigationIfEnemy(current, baseDir, next, path, boardInfo);
    }

    private Path navigationIfEnemy(Position current, Direction baseDir, Position next,
                                   Path path, BoardInfo boardInfo) {
        next = baseDir.move(next);
        if (boardInfo.isAlly(current, next)) {
            return path;
        }
        path.makePath(next);
        return path;
    }
}
