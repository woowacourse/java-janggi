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

public final class Horse extends OrthogonalRotateMoveBehavior {

    @Override
    protected List<Vectors> getVectorsList() {
        return List.of(Vectors.of(Movement.DOWN, Movement.LEFT_DOWN), Vectors.of(Movement.DOWN, Movement.RIGHT_DOWN));
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

        Position midPosition = currentPosition.moveToNextPosition(vectors.accumulate(0));
        Position finalPosition = currentPosition.moveToNextPosition(vectors.accumulate(1));

        if (board.hasPiece(midPosition)) {
            return;
        }

        if (board.canMoveToPosition(team, finalPosition)) {
            result.add(finalPosition);
        }
    }

    @Override
    public String toName() {
        return PieceType.HORSE.getName();
    }

    @Override
    public int toScore() {
        return PieceType.HORSE.getScore();
    }
}
