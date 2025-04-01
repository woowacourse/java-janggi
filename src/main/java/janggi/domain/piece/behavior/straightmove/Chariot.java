package janggi.domain.piece.behavior.straightmove;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.move.Position;
import janggi.domain.move.Vector;
import janggi.domain.piece.BoardPositionInfo;
import janggi.domain.piece.PieceType;
import java.util.Set;

public final class Chariot extends StraightMoveBehavior {

    @Override
    protected void searchAvailableMoves(Set<Position> result, BoardPositionInfo boardPositionInfo, Vector vector) {
        Board board = boardPositionInfo.board();
        Position currentPosition = boardPositionInfo.position();
        Team team = boardPositionInfo.team();

        if (board.hasPiece(currentPosition)) {
            addPositionIfNotSameSide(result, board, currentPosition, team);
            return;
        }
        result.add(currentPosition);

        if (currentPosition.canNotMove(vector)) {
            return;
        }
        Position nextPosition = currentPosition.moveToNextPosition(vector);

        searchAvailableMoves(result, boardPositionInfo.movePosition(nextPosition), vector);
    }

    @Override
    public String toName() {
        return PieceType.CHARIOT.getName();
    }

    @Override
    public int toScore() {
        return PieceType.CHARIOT.getScore();
    }

    private void addPositionIfNotSameSide(Set<Position> result, Board board, Position currentPosition, Team team) {
        if (board.isSameSide(team, currentPosition)) {
            return;
        }
        result.add(currentPosition);
    }
}
