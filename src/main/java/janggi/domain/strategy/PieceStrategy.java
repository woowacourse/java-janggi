package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Destinations;
import java.util.EnumSet;

public abstract class PieceStrategy implements MoveStrategy {

    @Override
    public Destinations moveablePositions(Position currentPosition, EnumSet<Direction> baseDirections,
                                            BoardInfo boardInfo) {
        Destinations destinations = new Destinations();
        for (Direction baseDirection : baseDirections) {
            destinations = destinations.addDestinations(navigationPath(currentPosition, baseDirection, boardInfo));
        }
        return destinations;
    }

    protected abstract Destinations navigationPath(Position current, Direction baseDir, BoardInfo boardInfo);
}
