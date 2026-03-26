package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class GuardMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        if (from.getRow() == to.getRow()) {
            if (Math.abs(from.getCol() - to.getCol()) != 1) {
                return false;
            }
        }
        if (from.getCol() == to.getCol()) {
            if (Math.abs(from.getRow() - to.getRow()) != 1) {
                return false;
            }
        }

        if (board.isAnotherTeam(from, to)) {
            return true;
        }
        return false;
    }
}
