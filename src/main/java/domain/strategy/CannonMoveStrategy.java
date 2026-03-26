package domain.strategy;

import domain.Board;
import domain.Type;
import domain.vo.Position;

public class CannonMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        if (isNotCorrectPath(from, to)) 
            return false;

        int nx = 0, ny = 0;
        if (from.getRow() < to.getRow()) {
            nx = 1;
        }
        if (from.getRow() > to.getRow()) {
            nx = -1;
        }
        if (from.getCol() < to.getCol()) {
            ny = 1;
        }
        if (from.getCol() > to.getCol()) {
            ny = -1;
        }

        int pieceCount = 0;
        int row = from.getRow();
        int col = from.getCol();
        while (true) {
            row += nx;
            col += ny;

            if (row == to.getRow() && col == to.getCol()) {
                if (!isCannon(board, to.getRow(), to.getCol())
                        && board.isAnotherTeam(from, to)
                        && pieceCount == 1) {
                    return true;
                }

                return false;
            }

            if (board.isExistPosition(Position.of(row, col))) {
                if (isCannon(board, row, col)) {
                    return false;
                }

                pieceCount += 1;
            }

            if (pieceCount > 1) {
                return false;
            }
        }
    }

    private boolean isCannon(Board board, int row, int col) {
        if (board.findPieceByPosition(Position.of(row, col)).isEmpty()) return false;
        return board.findPieceByPosition(Position.of(row, col)).get().getType() == Type.CANNON;
    }

    private boolean isNotCorrectPath(Position from, Position to) {
        if (from.getRow() != to.getRow() && from.getCol() != to.getCol()) {
            return true;
        }
        return false;
    }
}
