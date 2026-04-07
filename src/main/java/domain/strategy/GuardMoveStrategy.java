package domain.strategy;

import domain.board.Board;
import domain.board.Piece;
import domain.board.Team;
import domain.vo.Position;

public class GuardMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isOutsideOwnPalace(from, to, board))
            return false;

        if (!from.isOneStepStraightTo(to)
                && !MoveValidator.canMoveOneStepDiagonal(from, to, board))
            return false;

        return board.canOccupy(from, to);
    }

    private boolean isOutsideOwnPalace(Position from, Position to, Board board) {
        Piece piece = board.findPieceByPosition(from)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동할 기물이 없습니다."));
        Team team = piece.getTeam();

        if (!board.isInOwnPalace(from, team)) {
            return true;
        }

        if (!board.isInOwnPalace(to, team)) {
            return true;
        }
        return false;
    }
}
