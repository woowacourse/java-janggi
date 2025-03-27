package janggi.domain.piece.behavior.rotatemove;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.move.Vectors;
import java.util.List;
import java.util.Set;

public final class Elephant extends OrthogonalRotateMoveBehavior {

    @Override
    protected List<Vectors> getVectorsList() {
        return List.of(
                Vectors.of(Movement.DOWN, Movement.LEFT_DOWN, Movement.LEFT_DOWN),
                Vectors.of(Movement.DOWN, Movement.RIGHT_DOWN, Movement.RIGHT_DOWN)
        );
    }

    @Override
    protected void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition,
                                        List<Vectors> vectorsList,
                                        Team team) {
        for (Vectors vectors : vectorsList) {
            searchAvailableMove(result, board, currentPosition, team, vectors);
        }
    }

    @Override
    protected void searchAvailableMove(Set<Position> result, Board board, Position currentPosition, Team team,
                                       Vectors vectors) {
        if (canNotMove(vectors, currentPosition)) {
            return;
        }

        if (hasNotAvailableMiddleMove(vectors, currentPosition, board)) {
            return;
        }

        Position finalPosition = currentPosition.moveToNextPosition(vectors.accumulate(2));

        if (board.canMoveToPosition(team, finalPosition)) {
            result.add(finalPosition);
        }
    }

    @Override
    public String toName() {
        return "상";
    }

    @Override
    public int toScore() {
        return 3;
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
