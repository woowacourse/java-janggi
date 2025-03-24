package janggi.domain.piece.behavior.rotatemove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.move.Vectors;
import java.util.List;
import java.util.Set;

public final class Horse extends RotateMoveBehavior {

    @Override
    protected List<Vectors> getVectorsList() {
        return List.of(Vectors.of(Movement.DOWN, Movement.LEFT_DOWN), Vectors.of(Movement.DOWN, Movement.RIGHT_DOWN));
    }

    @Override
    protected void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, List<Vectors> vectorsList,
                                        Side side) {
        for (Vectors vectors : vectorsList) {
            searchAvailableMove(result, board, currentPosition, side, vectors);
        }
    }

    @Override
    protected void searchAvailableMove(Set<Position> result, Board board, Position currentPosition, Side side,
                                       Vectors vectors) {
        if (canNotMove(vectors, currentPosition)) {
            return;
        }

        Position midPosition = currentPosition.moveToNextPosition(vectors.accumulate(0));
        Position finalPosition = currentPosition.moveToNextPosition(vectors.accumulate(1));

        if (board.hasPiece(midPosition)) {
            return;
        }

        if (board.canMoveToPosition(side, finalPosition)) {
            result.add(finalPosition);
        }
    }

    @Override
    public String toName() {
        return "마";
    }
}
