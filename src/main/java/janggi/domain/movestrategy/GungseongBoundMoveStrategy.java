package janggi.domain.movestrategy;

import janggi.domain.BoardState;
import janggi.domain.position.Position;
import janggi.domain.Team;

public class GungseongBoundMoveStrategy implements MoveStrategy {
    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        int fromRow = from.getRow();
        int fromCol = from.getColumn();
        int toRow = to.getRow();
        int toCol = to.getColumn();

        if (!(3 <= fromCol && fromCol <= 5) || !(3 <= toCol && toCol <= 5)) {
            return false;
        }

        Team currentTeam = boardState.getPieceAt(from).getTeam();

        if (currentTeam == Team.HAN) {
            if (!(0 <= fromRow && fromRow <= 2) || !(0 <= toRow && toRow <= 2)) {
                return false;
            }
        }

        if (currentTeam == Team.CHO) {
            if (!(7 <= fromRow && fromRow <= 9) || !(7 <= toRow && toRow <= 9)) {
                return false;
            }
        }

        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        if (fromCol == toCol && Math.abs(fromRow - toRow) != 1) {
            return false;
        }

        if (fromRow == toRow && Math.abs(fromCol - toCol) != 1) {
            return false;
        }

        return true;
    }
}
