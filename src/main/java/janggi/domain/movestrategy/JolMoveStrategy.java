package janggi.domain.movestrategy;

import janggi.domain.BoardState;
import janggi.domain.position.Position;
import janggi.domain.Team;

public class JolMoveStrategy implements MoveStrategy {
    private static final int HAN_FORWARD = 1;
    private static final int CHO_FORWARD = -1;
    public static final int NEXT_TO = 1;

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        int fromRow = from.getRow();
        int fromCol = from.getColumn();
        int toRow = to.getRow();
        int toCol = to.getColumn();

        if (fromRow == toRow && Math.abs(fromCol - toCol) == NEXT_TO) {
            return true;
        }

        Team currentTeam = boardState.getPieceAt(from).getTeam();

        if (currentTeam == Team.HAN) {
            if (fromCol == toCol && toRow - fromRow == HAN_FORWARD) {
                return true;
            }
        }

        if (currentTeam == Team.CHO) {
            if (fromCol == toCol && toRow - fromRow == CHO_FORWARD) {
                return true;
            }
        }

        return false;
    }
}
