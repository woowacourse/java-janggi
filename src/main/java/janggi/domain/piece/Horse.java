package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import janggi.domain.Vectors;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Horse implements PieceBehavior {

    private static final List<Vectors> VECTOR_ROUTE_LIST = List.of(
            Vectors.of(new Vector(1, 0), new Vector(2, -1)),
            Vectors.of(new Vector(1, 0), new Vector(2, 1))
    );

    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        List<Vectors> rotateVectors = new ArrayList<>(VECTOR_ROUTE_LIST);
        for (int i = 0; i < 4; i++) {
            rotateVectors = Vectors.rotate(rotateVectors);

            searchAvailableMoves(result, board, position, rotateVectors, side);
        }

        return result;
    }

    @Override
    public String toName() {
        return "마";
    }

    private void searchAvailableMoves(Set<Position> result, Board board, Position position, List<Vectors> vectorsList,
                                      Side side) {
        for (Vectors vectors : vectorsList) {
            searchAvailableMove(result, board, position, side, vectors.getVectors());
        }
    }

    private void searchAvailableMove(Set<Position> result, Board board, Position position, Side side,
                                     List<Vector> vectors) {
        if (canNotMove(vectors, position)) {
            return;
        }

        Position midPosition = position.moveToNextPosition(vectors.get(0));
        Position finalPosition = position.moveToNextPosition(vectors.get(1));

        if (board.hasPiece(midPosition)) {
            return;
        }

        if (board.canMoveToPosition(side, finalPosition)) {
            result.add(finalPosition);
        }
    }

    private boolean canNotMove(List<Vector> vectors, Position currentPosition) {
        return !vectors.stream()
                .allMatch(currentPosition::canMove);
    }
}
