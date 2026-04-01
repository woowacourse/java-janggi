package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Path;
import java.util.List;

public class HorseMoveStrategy extends PieceStrategy {

    @Override
    protected Path navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        if (!baseDir.canMove(current)) {
            return new Path();
        }
        Position firstStep = baseDir.move(current);
        if (!boardInfo.isEmpty(firstStep)) {
            return new Path();
        }
        List<Direction> nextDirections = baseDir.nextDiagonalDirections();
        return navigationIfEnemy(current, nextDirections, firstStep, boardInfo);
    }

    private Path navigationIfEnemy(Position current, List<Direction> nextDirections, Position firstStep,
                                   BoardInfo boardInfo) {
        Path path = new Path();
        nextDirections.forEach(
                targetDirection -> navigationIfEnemy(current, targetDirection, firstStep, path, boardInfo));
        return path;
    }

    private void navigationIfEnemy(Position current, Direction targetDirection, Position firstStep, Path path,
                                   BoardInfo boardInfo) {
        if (!targetDirection.canMove(firstStep)) {
            return;
        }
        Position nextStep = targetDirection.move(firstStep);
        if (!boardInfo.isAlly(current, nextStep)) {
            path.makePath(nextStep);
        }
    }
}
