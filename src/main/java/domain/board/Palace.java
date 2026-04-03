package domain.board;

import domain.piece.Camp;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Palace {
    private final Map<PositionPair, List<Position>> diagonalPaths = Map.ofEntries(
            Map.entry(makePair(4, 1, 5, 2), path()),
            Map.entry(makePair(5, 2, 6, 3), path()),
            Map.entry(makePair(4, 1, 6, 3), path(5, 2)),

            Map.entry(makePair(6, 1, 5, 2), path()),
            Map.entry(makePair(5, 2, 4, 3), path()),
            Map.entry(makePair(6, 1, 4, 3), path(5, 2)),

            Map.entry(makePair(4, 8, 5, 9), path()),
            Map.entry(makePair(5, 9, 6, 10), path()),
            Map.entry(makePair(4, 8, 6, 10), path(5, 9)),

            Map.entry(makePair(6, 8, 5, 9), path()),
            Map.entry(makePair(5, 9, 4, 10), path()),
            Map.entry(makePair(6, 8, 4, 10), path(5, 9))
    );

    public boolean contains(Camp camp, Position position) {
        if (position.x() < 4 || position.x() > 6) {
            return false;
        }

        if (camp == Camp.HAN) {
            return position.y() >= 1 && position.y() <= 3;
        }

        return position.y() >= 8 && position.y() <= 10;
    }

    public Optional<List<Position>> findDiagonalPath(Position from, Position to) {
        return Optional.ofNullable(diagonalPaths.get(new PositionPair(from, to)));
    }

    private PositionPair makePair(int fromX, int fromY, int toX, int toY) {
        return new PositionPair(
                new Position(fromX, fromY),
                new Position(toX, toY)
        );
    }

    private List<Position> path() {
        return List.of();
    }

    private List<Position> path(int x, int y) {
        return List.of(new Position(x, y));
    }
}
