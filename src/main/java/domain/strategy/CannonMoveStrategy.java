package domain.strategy;

import domain.Board;
import domain.Type;
import domain.vo.Position;

public class CannonMoveStrategy implements MoveStrategy {

    private static final int CANNON_REQUIRED_PIECE_COUNT = 1;

    @Override
    public boolean canMove(Position from, Position to, Board board) {
        if (isNotCorrectPath(from, to)) 
            return false;

        int nx = determineNx(from, to);
        int ny = determineNy(from, to);

        int pieceCount = 0;
        int row = from.getRow();
        int col = from.getCol();
        while (true) {
            row += nx;
            col += ny;

            if (row == to.getRow() && col == to.getCol()) {
                return isCannonValidTarget(from, to, board, pieceCount);
            }

            if (board.isExistPosition(Position.of(row, col))) {
                if (isCannon(board, row, col)) {
                    return false;
                }

                pieceCount += 1;
            }

            if (pieceCount > CANNON_REQUIRED_PIECE_COUNT) {
                return false;
            }
        }
    }

    private boolean isCannonValidTarget(Position from, Position to, Board board, int pieceCount) {
        if (!isCannon(board, to.getRow(), to.getCol())
                && board.isAnotherTeam(from, to)
                && pieceCount == CANNON_REQUIRED_PIECE_COUNT) {
            return true;
        }
        return false;
    }

    private int determineNx(Position from, Position to) {
        if (from.getRow() < to.getRow()) {
            return 1;
        }
        if (from.getRow() > to.getRow()) {
            return -1;
        }
        return 0;
    }

    private int determineNy(Position from, Position to) {
        if (from.getCol() < to.getCol()) {
            return 1;
        }
        if (from.getCol() > to.getCol()) {
            return -1;
        }

        return 0;
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
