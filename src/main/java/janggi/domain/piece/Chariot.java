package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Chariot implements PieceBehavior {

    private static final List<Vector> VECTORS = List.of(
            new Vector(1, 0),
            new Vector(0, -1),
            new Vector(0, 1),
            new Vector(-1, 0));

    @Override
    public String toName() {
        return "차";
    }

    @Override
    public Set<Position> generateMovePosition(Side side, Position position) {
        return Set.of();
    }

    @Override
    public Set<Position> generateMovePosition(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        for (Vector vector : VECTORS) {
            position.calculate(vector)
                    .ifPresent(movePosition ->
                            dfs(result, board, movePosition, vector, side));
        }

        return result;
    }

    public void dfs(Set<Position> result, Board board, Position currentPosition, Vector vector, Side side) {
        if (board.hasPosition(currentPosition)) {
            processBySide(result, board, currentPosition, side);
            return;
        }

        result.add(currentPosition);

        Optional<Position> calculate = currentPosition.calculate(vector);
        if (calculate.isEmpty()) {
            return;
        }

        dfs(result, board, calculate.get(), vector, side);
    }

    private void processBySide(Set<Position> result, Board board, Position currentPosition, Side side) {
        if (board.isSameSide(side, currentPosition)) {
            return;
        }
        result.add(currentPosition);
    }
}
