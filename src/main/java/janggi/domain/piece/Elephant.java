package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.Vector;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Elephant implements PieceBehavior {

    private static final List<List<Vector>> VECTORS_LIST = List.of(
            List.of(new Vector(1, 0), new Vector(2, -1), new Vector(3, -2)),
            List.of(new Vector(1, 0), new Vector(2, 1), new Vector(3, 2))
    );

    @Override
    public String toName() {
        return "상";
    }

    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        List<List<Vector>> rotateVectors = new ArrayList<>(VECTORS_LIST);
        for (int i = 0; i < 4; i++) {
            rotateVectors = Vector.rotate(rotateVectors);

            searchAvailableMoves(result, board, position, rotateVectors, side);
        }

        return result;
    }

    private void searchAvailableMoves(Set<Position> result, Board board, Position position, List<List<Vector>> vectorsList,
                                      Side side) {
        for (List<Vector> vectors : vectorsList) {
            addAvailableMove(result, board, position, side, vectors);
        }
    }

    private void addAvailableMove(Set<Position> result, Board board, Position position, Side side, List<Vector> vectors) {
        if (canNotMove(vectors, position)) {
            return;
        }

        if (hasNotAvailableMiddleMove(vectors, position, board)) {
            return;
        }

        Position finalPosition = position.moveToNextPosition(vectors.get(2));

        if (board.canMoveToPosition(side, finalPosition)) {
            result.add(finalPosition);
        }
    }

    private boolean checkAvailableMiddleMove(Position midPosition, Board board) {
        return midPosition != null && !board.hasPiece(midPosition);
    }

    private boolean canNotMove(List<Vector> vectors, Position currentPosition) {
        return !vectors.stream()
                .allMatch(currentPosition::canMove);
    }

    private boolean hasNotAvailableMiddleMove(List<Vector> vectors, Position currentPosition, Board board) {
        Position midPosition1 = currentPosition.moveToNextPosition(vectors.get(0));
        Position midPosition2 = currentPosition.moveToNextPosition(vectors.get(1));

        return !(checkAvailableMiddleMove(midPosition1, board) && checkAvailableMiddleMove(midPosition2, board));
    }
}
