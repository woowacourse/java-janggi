package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Destinations;
import java.util.List;

public class SlideMoveStrategy extends PieceStrategy {

    @Override
    protected Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        Destinations destinations = new Destinations();
        Position next = current;
        while (baseDir.canMove(next) && boardInfo.isEmpty(baseDir.move(next))) {
            next = baseDir.move(next);
            destinations = destinations.addDestination(next);
        }
        if (!baseDir.canMove(next)) {
            return destinations;
        }
        return destinations.addDestinations(navigationIfEnemy(current, baseDir, next, boardInfo));
    }

    private Destinations navigationIfEnemy(Position current, Direction baseDir, Position next,
                                           BoardInfo boardInfo) {
        next = baseDir.move(next);
        if (boardInfo.isAlly(current, next)) {
            return new Destinations();
        }
        return new Destinations(List.of(next));
    }
}
