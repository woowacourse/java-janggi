package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class HorseMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        if (isNotCorrectPath(from, to))
            return false;

        int nx = to.getRow() - from.getRow();
        int ny = to.getCol() - from.getCol();

        if (board.isExistPosition(Position.of((from.getRow() + nx / 2), (from.getCol() + ny / 2)))) {
            return false;
        }
        if (board.isAnotherTeam(from, to)) {
            return true;
        }

        return false;
    }

    private boolean isNotCorrectPath(Position from, Position to) {
        if (Math.abs(from.getRow() - to.getRow()) == 1) {
            if (Math.abs(from.getCol() - to.getCol()) != 2) {
                return true;
            }
        }
        if (Math.abs(from.getRow() - to.getRow()) == 2) {
            if (Math.abs(from.getCol() - to.getCol()) != 1)
                return true;
        }
        return false;
    }
}
