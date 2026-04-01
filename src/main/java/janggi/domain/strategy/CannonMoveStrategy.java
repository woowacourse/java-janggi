package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Path;

public class CannonMoveStrategy extends PieceStrategy {

    @Override
    protected Path navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        Position target = firstMoveablePosition(current, baseDir, boardInfo);
        if (boardInfo.isCannon(target)) {
            return new Path();
        }
        return navigationMoveablePosition(current, baseDir, target, boardInfo);
    }

    private Position firstMoveablePosition(Position current, Direction baseDir, BoardInfo boardInfo) {
        if (!baseDir.canMove(current) || !boardInfo.isEmpty(baseDir.move(current))) {
            return current;
        }
        while (baseDir.canMove(current) && boardInfo.isEmpty(current)) {
            current = baseDir.move(current);
        }
        return current;
    }

    private Path navigationMoveablePosition(Position current, Direction baseDir, Position target,
                                            BoardInfo boardInfo) {
        Path path = new Path();
        while (baseDir.canMove(target) && boardInfo.isEmpty(baseDir.move(target))) {
            target = baseDir.move(target);
            path.makePath(target);
        }
        if (!baseDir.canMove(target)) {
            return path;
        }
        return navigationIfEnemy(current, baseDir, target, path, boardInfo);
    }

    private Path navigationIfEnemy(Position current, Direction baseDir, Position next,
                                   Path path, BoardInfo boardInfo) {
        next = baseDir.move(next);
        if (boardInfo.isAlly(current, next) || boardInfo.isCannon(next)) {
            return path;
        }
        path.makePath(next);
        return path;
    }
}
