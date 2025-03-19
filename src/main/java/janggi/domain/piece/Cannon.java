package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Cannon implements PieceBehavior {

    private static final List<Vector> VECTORS = List.of(
            new Vector(1, 0),
            new Vector(0, -1),
            new Vector(0, 1),
            new Vector(-1, 0));

    @Override
    public String toName() {
        return "포";
    }

    @Override
    public Set<Position> generateMovePosition(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        for (Vector vector : VECTORS) {
            position.calculate(vector)
                    .ifPresent(movePosition ->
                            dfs(result, board, movePosition, vector, side, false));
        }

        System.out.println(result);
        return result;
    }

    public void dfs(Set<Position> result, Board board, Position currentPosition, Vector vector, Side side,
                    boolean hasPassed) {
        if (board.isCannon(currentPosition)) {
            return;
        }

        if (hasPassed) {
            Optional<Position> calculate = currentPosition.calculate(vector);

            if (calculate.isEmpty()) {
                return;
            }
            // 그런데 다음 지점이 기물이 존재하는데
            if (board.hasPiece(calculate.get())) {
                if (!board.isSameSide(side, calculate.get())) {
                    result.add(calculate.get());
                }
                return;
            }

            result.add(calculate.get());
            dfs(result, board, calculate.get(), vector, side, true);
        }

        Optional<Position> calculate = currentPosition.calculate(vector);
        if (calculate.isEmpty()) {
            return;
        }

        if (board.hasPiece(calculate.get())) {
            dfs(result, board, calculate.get(), vector, side, true);
        }

        dfs(result, board, calculate.get(), vector, side, false);
    }
}
