package domain.strategy;

import domain.Board;
import domain.Piece;
import domain.vo.Position;

public class ElephantMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        int currentRow = from.getRow();
        int currentCol = from.getCol();

        int targetRow = to.getRow();
        int targetCol = to.getCol();

        if (Math.abs(currentRow - targetRow) == 3) {
            if (targetRow > currentRow) {
                currentRow += 1;
                if (board.isExistPosition(Position.of(currentRow, currentCol))) {
                    return false;
                }
                if (currentCol < targetCol) {
                    if (board.isExistPosition(Position.of(currentRow + 1, currentCol + 1))) {
                        return false;
                    }
                    if (board.isExistPosition(Position.of(currentRow + 2, currentCol + 2))) {
                        return isOtherTeam(board, currentRow + 2, currentCol + 2, targetRow, targetCol);
                    }
                    return true;
                }
                if (currentCol > targetCol) {
                    if (board.isExistPosition(Position.of(currentRow + 1, currentCol - 1))) {
                        return false;
                    }
                    if (board.isExistPosition(Position.of(currentRow + 2, currentCol - 2))) {
                        return isOtherTeam(board, currentRow + 2, currentCol - 2, targetRow, targetCol);
                    }
                    return true;
                }
            }

            if (targetRow < currentRow) {
                currentRow -= 1;
                if (board.isExistPosition(Position.of(currentRow, currentCol))) {
                    return false;
                }
                if (currentCol < targetCol) {
                    if (board.isExistPosition(Position.of(currentRow - 1, currentCol + 1))) {
                        return false;
                    }
                    if (board.isExistPosition(Position.of(currentRow - 2, currentCol + 2))) {
                        return isOtherTeam(board, currentRow - 2, currentCol + 2, targetRow, targetCol);
                    }
                    return true;
                }
                if (currentCol > targetCol) {
                    if (board.isExistPosition(Position.of(currentRow - 1, currentCol - 1))) {
                        return false;
                    }
                    if (board.isExistPosition(Position.of(currentRow - 2, currentCol - 2))) {
                        return isOtherTeam(board, currentRow - 2, currentCol - 2, targetRow, targetCol);
                    }
                    return true;
                }
            }
        }

        if (Math.abs(currentRow - targetRow) == 2) {
            if (targetCol > currentCol) {
                currentCol += 1;
                if (board.isExistPosition(Position.of(currentRow, currentCol))) {
                    return false;
                }

                if (currentRow < targetRow) {
                    if (board.isExistPosition(Position.of(currentRow + 1, currentCol + 1))) {
                        return false;
                    }
                    if (board.isExistPosition(Position.of(currentRow + 2, currentCol + 2))) {
                        return isOtherTeam(board, currentRow + 2, currentCol + 2, targetRow, targetCol);
                    }
                    return true;
                }
                if (currentRow > targetRow) {
                    if (board.isExistPosition(Position.of(currentRow - 1, currentCol + 1))) {
                        return false;
                    }
                    if (board.isExistPosition(Position.of(currentRow - 2, currentCol + 2))) {
                        return isOtherTeam(board, currentRow - 2, currentCol + 2, targetRow, targetCol);
                    }
                    return true;
                }
            }

            if (targetCol < currentCol) {
                currentCol -= 1;
                if (board.isExistPosition(Position.of(currentRow, currentCol))) {
                    return false;
                }

                if (currentRow < targetRow) {
                    if (board.isExistPosition(Position.of(currentRow + 1, currentCol - 1))) {
                        return false;
                    }
                    if (board.isExistPosition(Position.of(currentRow + 2, currentCol - 2))) {
                        return isOtherTeam(board, currentRow + 2, currentCol - 2, targetRow, targetCol);
                    }
                    return true;
                }
                if (currentRow > targetRow) {
                    if (board.isExistPosition(Position.of(currentRow - 1, currentCol - 1))) {
                        return false;
                    }
                    if (board.isExistPosition(Position.of(currentRow - 2, currentCol - 2))) {
                        return isOtherTeam(board, currentRow - 2, currentCol - 2, targetRow, targetCol);
                    }
                    return true;
                }
            }
        }

        return true;
    }

    private boolean isOtherTeam(Board board, int currentRow, int currentCol, int targetRow, int targetCol) {
        Piece currentPiece = board.findPieceByPosition(Position.of(currentRow, currentCol));
        Piece targetPiece = board.findPieceByPosition(Position.of(targetRow, targetCol));
        return currentPiece.getTeam() != targetPiece.getTeam();
    }
}
