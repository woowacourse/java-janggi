package domain.board;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Palace {
    private static final int MIN_X = 4;
    private static final int MAX_X = 6;

    private final Map<Set<Position>, List<Position>> diagonalPaths = Map.ofEntries(
            Map.entry(Set.of(new Position(4, 1), new Position(5, 2)), List.of()),
            Map.entry(Set.of(new Position(5, 2), new Position(6, 3)), List.of()),
            Map.entry(Set.of(new Position(4, 1), new Position(6, 3)), List.of(new Position(5, 2))),

            Map.entry(Set.of(new Position(6, 1), new Position(5, 2)), List.of()),
            Map.entry(Set.of(new Position(5, 2), new Position(4, 3)), List.of()),
            Map.entry(Set.of(new Position(6, 1), new Position(4, 3)), List.of(new Position(5, 2))),

            Map.entry(Set.of(new Position(4, 8), new Position(5, 9)), List.of()),
            Map.entry(Set.of(new Position(5, 9), new Position(6, 10)), List.of()),
            Map.entry(Set.of(new Position(4, 8), new Position(6, 10)), List.of(new Position(5, 9))),

            Map.entry(Set.of(new Position(6, 8), new Position(5, 9)), List.of()),
            Map.entry(Set.of(new Position(5, 9), new Position(4, 10)), List.of()),
            Map.entry(Set.of(new Position(6, 8), new Position(4, 10)), List.of(new Position(5, 9)))
    );

    public Optional<List<Position>> findDiagonalPath(Position from, Position to) {
        return Optional.ofNullable(diagonalPaths.get(Set.of(from, to)));
    }
}
