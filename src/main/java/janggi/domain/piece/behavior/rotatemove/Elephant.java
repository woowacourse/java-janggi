package janggi.domain.piece.behavior.rotatemove;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Movement;
import janggi.domain.move.Position;
import janggi.domain.move.Vectors;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.PieceType;
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
    protected void searchAvailableMoves(Set<Position> result, BoardPositionInfo boardPositionInfo,
                                        List<Vectors> vectorsList) {
        for (Vectors vectors : vectorsList) {
            searchAvailableMove(result, boardPositionInfo, vectors);
        }
    }

    @Override
    protected void searchAvailableMove(Set<Position> result, BoardPositionInfo boardPositionInfo,
                                       Vectors vectors) {
        Board board = boardPositionInfo.board();
        Position currentPosition = boardPositionInfo.position();
        Team team = boardPositionInfo.team();

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
        return PieceType.ELEPHANT.getName();
    }

    @Override
    public int toScore() {
        return PieceType.ELEPHANT.getScore();
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
