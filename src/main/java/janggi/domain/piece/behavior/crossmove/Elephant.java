package janggi.domain.piece.behavior.crossmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.move.Vectors;
import java.util.List;
import java.util.Set;

public class Elephant extends CrossMoveBehavior {

    @Override
    protected List<Vectors> getVectorsList() {
        return List.of(
                Vectors.of(new Vector(1, 0), new Vector(2, -1), new Vector(3, -2)),
                Vectors.of(new Vector(1, 0), new Vector(2, 1), new Vector(3, 2))
        );
    }

    @Override
    protected void searchAvailableMoves(Set<Position> result, Board board, Position position,
                                        List<Vectors> vectorsList,
                                        Side side) {
        for (Vectors vectors : vectorsList) {
            searchAvailableMove(result, board, position, side, vectors.vectors());
        }
    }

    @Override
    protected void searchAvailableMove(Set<Position> result, Board board, Position position, Side side,
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

    @Override
    public String toName() {
        return "상";
    }

    private boolean checkAvailableMiddleMove(Position midPosition, Board board) {
        return midPosition != null && !board.hasPiece(midPosition);
    }

    private boolean hasNotAvailableMiddleMove(List<Vector> vectors, Position currentPosition, Board board) {
        Position midPosition1 = currentPosition.moveToNextPosition(vectors.get(0));
        Position midPosition2 = currentPosition.moveToNextPosition(vectors.get(1));

        return !(checkAvailableMiddleMove(midPosition1, board) && checkAvailableMiddleMove(midPosition2, board));
    }
}
