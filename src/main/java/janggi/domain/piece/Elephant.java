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

public class Elephant implements PieceBehavior {

    private static final List<Vectors> VECTORS_LIST = List.of(
            Vectors.of(new Vector(1, 0), new Vector(2, -1), new Vector(3, -2)),
            Vectors.of(new Vector(1, 0), new Vector(2, 1), new Vector(3, 2))
    );

    @Override
    public Set<Position> generateAvailableMovePositions(Board board, Side side, Position position) {
        Set<Position> result = new HashSet<>();
        List<Vectors> rotatedVectors = new ArrayList<>(VECTORS_LIST);
        for (int i = 0; i < 4; i++) {
            rotatedVectors = Vectors.rotate(rotatedVectors);

            searchAvailableMoves(result, board, position, rotatedVectors, side);
        }

        return result;
    }

    @Override
    public String toName() {
        return "상";
    }

    private void searchAvailableMoves(Set<Position> result, Board board, Position position,
                                      List<Vectors> vectorsList,
                                      Side side) {
        for (Vectors vectors : vectorsList) {
            List<Vector> vectorRoute = vectors.vectors();
            searchAvailableMove(result, board, position, side, vectorRoute);
        }
    }

    private void searchAvailableMove(Set<Position> result, Board board, Position position, Side side,
                                     List<Vector> vectors) {
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
        return vectors.stream()
                .allMatch(currentPosition::canNotMove);
    }

    private boolean hasNotAvailableMiddleMove(List<Vector> vectors, Position currentPosition, Board board) {
        Position midPosition1 = currentPosition.moveToNextPosition(vectors.get(0));
        Position midPosition2 = currentPosition.moveToNextPosition(vectors.get(1));

        return !(checkAvailableMiddleMove(midPosition1, board) && checkAvailableMiddleMove(midPosition2, board));
    }
}
