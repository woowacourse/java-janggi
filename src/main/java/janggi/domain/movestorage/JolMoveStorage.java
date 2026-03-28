package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Position;
import janggi.domain.Team;

import java.util.List;

public class JolMoveStorage implements MoveStorage{
    private static final int HAN_FORWARD = 1;
    private static final int CHO_FORWARD = -1;
    public static final int NEXT_TO = 1;

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        List<Integer> fromPosition = from.getPosition();
        List<Integer> toPosition = to.getPosition();

        int fromX = fromPosition.getFirst();
        int fromY = fromPosition.getLast();
        int toX = toPosition.getFirst();
        int toY = toPosition.getLast();

        if (Math.abs(fromX - toX) == NEXT_TO && fromY == toY) {
            return true;
        }

        if (boardState.getPieceAt(from).getTeam() == Team.HAN) {
            if (toY - fromY == HAN_FORWARD && fromX == toX) {
                return true;
            }
        }

        if (boardState.getPieceAt(from).getTeam() == Team.CHO) {
            if (toY - fromY == CHO_FORWARD && fromX == toX) {
                return true;
            }
        }
        
        return false;
    }
}
