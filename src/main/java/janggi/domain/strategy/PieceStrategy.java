package janggi.domain.strategy;

import janggi.domain.board.BoardInfo;
import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public abstract class PieceStrategy implements MoveStrategy {

    @Override
    public List<Position> destinationsOf(Position currentPosition, EnumSet<Direction> baseDirections,
                                         BoardInfo boardInfo) {
        List<Position> destinations = new ArrayList<>();
        Paths moveablePaths = findMovablePaths(currentPosition, baseDirections);
        for (Path route : moveablePaths) {
            destinations.addAll(validatePath(currentPosition, route, boardInfo));
        }
        return destinations;
    }

    private List<Position> validatePath(Position currentPosition, Path route, BoardInfo boardInfo) {
        List<Position> targetPositions = route.getPositions();
        boolean isAllPathValid = targetPositions.stream()
                .allMatch(targetPosition -> isAppendable(currentPosition, targetPosition, boardInfo));
        if (!isAllPathValid) {
            return Collections.emptyList();
        }
        return targetPositions;
    }

    public Paths findMovablePaths(Position currentPosition, EnumSet<Direction> baseDirections) {
        Paths paths = new Paths();
        for (Direction baseDir : baseDirections) {
            navigationPath(currentPosition, baseDir, paths);
        }
        return paths;
    }

    protected abstract void navigationPath(Position current, Direction baseDir, Paths paths);

    private boolean isAppendable(Position currentPosition, Position targetPosition, BoardInfo boardInfo) {
        if (boardInfo.isEmpty(targetPosition)) {
            return true;
        }
        return !boardInfo.isAlly(currentPosition, targetPosition);
    }
}
