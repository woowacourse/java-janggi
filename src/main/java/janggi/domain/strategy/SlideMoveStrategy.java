package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Destinations;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import java.util.List;

public class SlideMoveStrategy extends PieceStrategy {

    @Override
    protected Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo) {
        Destinations destinations = Destinations.empty();
        Position next = current;
        while (baseDir.canMove(next) && boardInfo.isEmpty(baseDir.move(next))) {
            next = baseDir.move(next);
            destinations = destinations.addDestinations(Destinations.of(List.of(next)));
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
            return Destinations.empty();
        }
        return Destinations.of(List.of(next));
    }
}
