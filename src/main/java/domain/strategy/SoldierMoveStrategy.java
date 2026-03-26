package domain.strategy;

import domain.Board;
import domain.Team;
import domain.vo.Position;

public class SoldierMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        if (isNotCorrectPath(from, to))
            return false;

        if (isWithdraw(from, to, board))
            return false;

        if (board.isAnotherTeam(from, to)) {
            return true;
        }

        return false;
    }

    private boolean isWithdraw(Position from, Position to, Board board) {
        if (board.findPieceByPosition(from).get().getTeam() == Team.CHU) {
            if (from.getRow() - to.getRow() == 1) {
                return true;
            }
        }

        if (board.findPieceByPosition(from).get().getTeam() == Team.HAN) {
            if (from.getRow() - to.getRow() == -1) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotCorrectPath(Position from, Position to) {
        if (from.getRow() == to.getRow()) {
            if (Math.abs(from.getCol() - to.getCol()) != 1) {
                return true;
            }
        }

        if (from.getCol() == to.getCol()) {
            if (Math.abs(from.getRow() - to.getRow()) != 1) {
                return true;
            }
        }
        return false;
    }
}
