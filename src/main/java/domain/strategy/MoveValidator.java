package domain.strategy;

import domain.board.Board;
import domain.vo.Position;

public class MoveValidator {

    public static boolean isNotOneStepStraight(final Position from, final Position to) {
        if (from.getRow() == to.getRow() && Math.abs(from.getCol() - to.getCol()) == 1) {
            return false;
        }

        if (from.getCol() == to.getCol() && Math.abs(from.getRow() - to.getRow()) == 1) {
            return false;
        }

        return true;
    }

    public static boolean isNotStraightPath(final Position from, final Position to) {
        return from.getCol() != to.getCol() && from.getRow() != to.getRow();
    }

    public static boolean isNotOneStepDiagonal(Position from, Position to, Board board) {
        if (board.canMoveDiagonallyInPalace(from, to)
                && Math.abs(from.getRow() - to.getRow()) == 1
                && Math.abs(from.getCol() - to.getCol()) == 1) {
            return false;
        }
        return true;
    }

    public static boolean isNotDiagonalPath(Position from, Position to, Board board) {
        if (board.canMoveDiagonallyInPalace(from, to)
                && Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getCol() - to.getCol())) {
            return false;
        }
        return true;
    }
}
