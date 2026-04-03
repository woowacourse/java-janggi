package domain.board;

import java.util.List;
import java.util.Optional;

public class Palace {
    public Optional<List<Position>> findDiagonalPath(Position from, Position to) {
        if (isSamePair(from, to, new Position(4, 1), new Position(6, 3))) {
            return Optional.of(List.of(new Position(5, 2)));
        }

        if (isSamePair(from, to, new Position(4, 1), new Position(5, 2))) {
            return Optional.of(List.of());
        }

        return Optional.empty();
    }

    private boolean isSamePair(Position from, Position to, Position first, Position second) {
        return (from.equals(first) && to.equals(second)) || (from.equals(second) && to.equals(first));
    }
}
