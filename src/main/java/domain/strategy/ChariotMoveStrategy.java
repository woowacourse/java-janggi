package domain.strategy;

import domain.Board;
import domain.vo.Position;

public class ChariotMoveStrategy implements MoveStrategy{

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        int currentRow = from.getRow();
        int currentCol = from.getCol();

        int targetRow = to.getRow();
        int targetCol = to.getCol();

        if (currentCol != targetCol && currentRow != targetRow) {
            return false;
        }

        if (currentCol == targetCol) {
            int min = Math.min(currentRow, targetRow) + 1;
            int max = Math.max(currentRow, targetRow);

            for (int temp = min; temp < max; temp++) {
                if (board.isExistPosition(Position.of(temp, currentCol))) {
                    return false;
                }
            }
        }

        if (targetRow == currentRow) {
            int min = Math.min(currentCol, targetCol) + 1;
            int max = Math.max(currentCol, targetCol);

            for (int temp = min; temp < max; temp++) {
                if (board.isExistPosition(Position.of(currentRow, temp))) {
                    return false;
                }
            }
        }

        return true;
    }
}
