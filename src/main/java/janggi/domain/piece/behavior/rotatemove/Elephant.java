package janggi.domain.piece.behavior.rotatemove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.move.Vectors;
import java.util.List;
import java.util.Set;

public final class Elephant extends RotateMoveBehavior {

    @Override
    protected List<Vectors> getVectorsList() {
        return List.of(
                Vectors.of(Movement.DOWN, Movement.LEFT_DOWN, Movement.LEFT_DOWN),
                Vectors.of(Movement.DOWN, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN)
        );
    }

    @Override
    protected void searchAvailableMoves(Set<Position> result, Board board, Position position,
                                        List<Vectors> vectorsList,
                                        Side side) {
        for (Vectors vectors : vectorsList) {
            searchAvailableMove(result, board, position, side, vectors);
        }
    }

    @Override
    protected void searchAvailableMove(Set<Position> result, Board board, Position position, Side side,
                                       Vectors vectors) {
        if (canNotMove(vectors, position)) {
            return;
        }

        if (hasNotAvailableMiddleMove(vectors, position, board)) {
            return;
        }

        Position finalPosition = position.moveToNextPosition(vectors.accumulate(2));

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

    private boolean hasNotAvailableMiddleMove(Vectors vectors, Position currentPosition, Board board) {
        Position midPosition1 = currentPosition.moveToNextPosition(vectors.accumulate(0));
        Position midPosition2 = currentPosition.moveToNextPosition(vectors.accumulate(1));

        return !(checkAvailableMiddleMove(midPosition1, board) && checkAvailableMiddleMove(midPosition2, board));
    }
}
