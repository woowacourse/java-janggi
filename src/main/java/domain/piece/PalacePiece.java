package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public abstract class PalacePiece extends Piece {
    public PalacePiece(Team team, PieceType type) {
        super(team, type);
    }

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        if (!isCorrectMoveDistanceAndDirection(from, to)) {
            return false;
        }

        if (isEmptySpace(to, board)) {
            return true;
        }

        return !board.hasSameTeamOn(to, this);
    }

    private boolean isEmptySpace(Position to, Board board) {
        return board.isEmpty(to);
    }

    private boolean isCorrectMoveDistanceAndDirection(Position from, Position to) {
        return (Math.abs(from.columnDistanceTo(to)) == 1 && from.rowDistanceTo(to) == 0)
                || (Math.abs(from.rowDistanceTo(to)) == 1 && from.columnDistanceTo(to) == 0);
    }
}
