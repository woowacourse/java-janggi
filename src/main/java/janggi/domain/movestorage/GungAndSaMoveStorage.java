package janggi.domain.movestorage;

import janggi.domain.BoardState;
import janggi.domain.Position;
import janggi.domain.Team;

import java.util.List;

public class GungAndSaMoveStorage implements MoveStorage{
    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        List<Integer> fromPosition = from.getPosition();
        List<Integer> toPosition = to.getPosition();

        int fromX = fromPosition.getFirst();
        int fromY = fromPosition.getLast();
        int toX = toPosition.getFirst();
        int toY = toPosition.getLast();

        if (!(3 <= fromX && fromX <= 5) || !(3 <= toX && toX <= 5)) {
            return false;
        }

        Team currentTeam = boardState.getPieceAt(from).getTeam();

        if (currentTeam == Team.HAN) {
            if (!(0 <= fromY && fromY <= 2) || !(0 <= toY && toY <= 2)) {
                return false;
            }
        }

        if (currentTeam == Team.CHO) {
            if (!(7 <= fromY && fromY <= 9) || !(7 <= toY && toY <= 9)) {
                return false;
            }
        }

        if (fromX != toX && fromY != toY) {
            return false;
        }

        if (fromY == toY && Math.abs(fromX - toX) != 1) {
            return false;
        }

        if (fromX == toX && Math.abs(fromY - toY) != 1) {
            return false;
        }

        return true;
    }
}
