package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public class Rook extends Piece{
    public Rook(Team team) {
        super(team, PieceType.ROOK);
    }

    @Override
    boolean canMove(Position from, Position to, Board board) {
        // 1. 도착 지점이 같은 열 또는 행이 아닌 경우 이동 불가
        if (!isCorrectMoveDistanceAndDirection(from, to)) {
            return false;
        }

        // 2. 도착지에 같은 팀이 존재하는 경우 이동 불가
        if (board.hasSameTeamOn(to, this)) {
            return false;
        }

        // 3. 도착지랑 출발지 사이에 말이 하나라도 존재하면 이동 불가
        if (!hasOnePieceInPath(from, to, board)) {
            return false;
        }

        return true;
    }

    private boolean isCorrectMoveDistanceAndDirection(Position from, Position to) {
        return from.isSameColumn(to) || from.isSameRow(to);
    }

    private boolean hasOnePieceInPath(Position from, Position to, Board board) {
        if (board.findPiecesInLinePath(from, to).isEmpty()) {
            return true;
        }
        return false;
    }
}
