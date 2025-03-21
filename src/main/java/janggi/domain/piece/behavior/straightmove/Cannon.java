package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Side;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import java.util.List;
import java.util.Set;

public class Cannon extends StraightMoveBehavior {

    // 포지션에서 isCannon을 봐서 처리할 수 있을 거 같음 외부로 나가지말고 포는 포 자체에서 처리하면 되기에 내부에서 처리하면 좋을 거 같다

    @Override
    public String toName() {
        return "포";
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    protected List<Vector> getVectors() {
        return List.of(
                new Vector(1, 0),
                new Vector(0, -1),
                new Vector(0, 1),
                new Vector(-1, 0));
    }

    @Override
    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Side side) {
        searchAvailableMoves(result, board, currentPosition, vector, side, board.hasPiece(currentPosition));
    }

    public void searchAvailableMoves(Set<Position> result, Board board, Position currentPosition, Vector vector,
                                     Side side,
                                     boolean hasPassed) {
        if (currentPosition.canNotMove(vector) || board.isCannon(currentPosition)) {
            return;
        }

        Position nextPosition = currentPosition.moveToNextPosition(vector);
        if (board.isCannon(nextPosition)) {
            return;
        }

        if (hasPassed && board.hasPiece(nextPosition) && !board.isSameSide(side, nextPosition)) {
            result.add(nextPosition);
            return;
        }

        if (hasPassed && board.hasPiece(nextPosition)) {
            return;
        }

        if (hasPassed) {
            result.add(nextPosition);
            searchAvailableMoves(result, board, nextPosition, vector, side, true);
        }

        searchAvailableMoves(result, board, nextPosition, vector, side, board.hasPiece(nextPosition));
    }
}
