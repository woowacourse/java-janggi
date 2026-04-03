package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import java.util.List;

public class CannonMoveStrategy extends PieceStrategy {

    @Override
    protected Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        if (!baseDir.canMove(current)) {
            return Destinations.empty();
        }
        Position target = firstMoveablePosition(current, baseDir, boardInfo);
        if (boardInfo.isCannon(target)) {
            return Destinations.empty();
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
        Destinations destinations = Destinations.empty();
        while (baseDir.canMove(target) && boardInfo.isEmpty(baseDir.move(target))) {
            target = baseDir.move(target);
            destinations = destinations.addDestinations(Destinations.of(List.of(target)));
        }
        if (!baseDir.canMove(target)) {
            return destinations;
        }
        return destinations.addDestinations(navigationIfEnemy(current, baseDir, target, boardInfo));
    }

    private Destinations navigationIfEnemy(Position current, Direction baseDir, Position next, BoardInfo boardInfo) {
        next = baseDir.move(next);
        if (boardInfo.isAlly(current, next) || boardInfo.isCannon(next)) {
            return Destinations.empty();
        }
        return Destinations.of(List.of(next));
    }
}
