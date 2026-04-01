package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public abstract class PieceStrategy implements MoveStrategy {

    public Paths findMovablePaths(Position currentPosition, EnumSet<Direction> baseDirections, BoardInfo boardInfo) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            paths.addPath(navigationPath(currentPosition, baseDir, boardInfo));
        }
        return paths;
    }

    @Override
    public List<Position> destinationsOf(Position currentPosition, EnumSet<Direction> baseDirections,
                                         BoardInfo boardInfo) {
        List<Position> destinations = new ArrayList<>();
        Paths moveablePaths = findMovablePaths(currentPosition, baseDirections, boardInfo);
        for (Path route : moveablePaths) {
            destinations.addAll(route.getPositions());
        }
        return destinations;
    }

    protected abstract Path navigationPath(Position current, Direction baseDir, BoardInfo boardInfo);
}
