package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.HashSet;
import java.util.List;
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
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        for (Vector vector : VECTORS) {
            position.calculateNextPosition(vector)
                    .ifPresent(movePosition ->
                            dfs(result, board, movePosition, vector, side, false));
        }

        return result;
    }

    public void dfs(Set<Position> result, Board board, Position currentPosition, Vector vector, Side side,
                    boolean hasPassed) {
        if (!currentPosition.canMove(vector) || board.isCannon(currentPosition)) {
            return;
        }

        Position nextPosition = currentPosition.moveToNextPosition(vector);

        if (hasPassed && board.hasPiece(nextPosition) && !board.isSameSide(side, nextPosition)) {
            result.add(nextPosition);
            return;
        }

        if (hasPassed && board.hasPiece(nextPosition)) {
            return;
        }

        if (hasPassed) {
            result.add(nextPosition);
            dfs(result, board, nextPosition, vector, side, true);
        }

        dfs(result, board, nextPosition, vector, side, board.hasPiece(nextPosition));
    }
}
