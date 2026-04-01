package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Destinations;
import java.util.List;

public class CannonMoveStrategy extends PieceStrategy {

    @Override
    protected Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        if (!baseDir.canMove(current)) {
            return new Destinations();
        }
        Position target = firstMoveablePosition(current, baseDir, boardInfo);
        if (boardInfo.isCannon(target)) {
            return new Destinations();
        }
        return navigationMoveablePosition(current, baseDir, target, boardInfo);
    }

    private Position firstMoveablePosition(Position current, Direction baseDir, BoardInfo boardInfo) {
        do {
            current = baseDir.move(current);
        } while (baseDir.canMove(current) && boardInfo.isEmpty(current));
        return current;
    }

    private Destinations navigationMoveablePosition(Position current, Direction baseDir, Position target,
                                                    BoardInfo boardInfo) {
        Destinations destinations = new Destinations();
        while (baseDir.canMove(target) && boardInfo.isEmpty(baseDir.move(target))) {
            target = baseDir.move(target);
            destinations = destinations.addDestination(target); // 재할당
        }
        if (!baseDir.canMove(target)) {
            return destinations;
        }
        return destinations.addDestinations(navigationIfEnemy(current, baseDir, target, boardInfo));
    }

    private Destinations navigationIfEnemy(Position current, Direction baseDir, Position next, BoardInfo boardInfo) {
        next = baseDir.move(next);
        if (boardInfo.isAlly(current, next) || boardInfo.isCannon(next)) {
            return new Destinations();
        }
        return new Destinations(List.of(next));
    }
}
