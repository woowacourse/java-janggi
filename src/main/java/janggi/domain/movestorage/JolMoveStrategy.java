package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Position;
import janggi.domain.Team;

public class JolMoveStrategy implements MoveStrategy {
    private static final int HAN_FORWARD = 1;
    private static final int CHO_FORWARD = -1;
    public static final int NEXT_TO = 1;

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        int fromX = from.getRow();
        int fromY = from.getColumn();
        int toX = to.getRow();
        int toY = to.getColumn();

        if (Math.abs(fromX - toX) == NEXT_TO && fromY == toY) {
            return true;
        }

        Team currentTeam = boardState.getPieceAt(from).getTeam();

        if (currentTeam == Team.HAN) {
            if (toY - fromY == HAN_FORWARD && fromX == toX) {
                return true;
            }
        }

        if (currentTeam == Team.CHO) {
            if (toY - fromY == CHO_FORWARD && fromX == toX) {
                return true;
            }
        }
        
        return false;
    }
}
