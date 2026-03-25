package domain.strategy;

import domain.Board;
import domain.Piece;
import domain.vo.Position;

public class ChariotMoveStrategy implements MoveStrategy{

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        int currentRow = from.getRow();
        int currentCol = from.getCol();

        int targetRow = to.getRow();
        int targetCol = to.getCol();

        if (checkStraightPath(currentCol, targetCol, currentRow, targetRow))
            return false;

        if (currentCol == targetCol) {
            int min = Math.min(currentRow, targetRow) + 1;
            int max = Math.max(currentRow, targetRow);

            if (checkClearPath(board, min, max, currentCol))
                return false;

            if (board.isExistPosition(Position.of(targetRow, targetCol))) {
                return isOtherTeam(board, currentRow, currentCol, targetRow, targetCol);
            }
        }

        if (targetRow == currentRow) {
            int min = Math.min(currentCol, targetCol) + 1;
            int max = Math.max(currentCol, targetCol);

            if (checkClearPath(board, min, max, currentCol))
                return false;

            if (board.isExistPosition(Position.of(targetRow, targetCol))) {
                return isOtherTeam(board, currentRow, currentCol, targetRow, targetCol);
            }
        }

        return true;
    }

    private boolean checkStraightPath(int currentCol, int targetCol, int currentRow, int targetRow) {
        return currentCol != targetCol && currentRow != targetRow;
    }

    private boolean isOtherTeam(Board board, int currentRow, int currentCol, int targetRow, int targetCol) {
        Piece currentPiece = board.findPieceByPosition(Position.of(currentRow, currentCol));
        Piece targetPiece = board.findPieceByPosition(Position.of(targetRow, targetCol));
        return currentPiece.getTeam() != targetPiece.getTeam();
    }

    private boolean checkClearPath(Board board, int min, int max, int currentCol) {
        for (int temp = min; temp < max; temp++) {
            if (board.isExistPosition(Position.of(temp, currentCol))) {
                return true;
            }
        }
        return false;
    }
}
