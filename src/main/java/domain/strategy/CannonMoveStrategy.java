package domain.strategy;

import domain.Board;
import domain.Type;
import domain.vo.Position;

public class CannonMoveStrategy implements MoveStrategy {

    private static final int CANNON_REQUIRED_PIECE_COUNT = 1;

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotCorrectPath(from, to)) 
            return false;

        int nx = Integer.compare(to.getRow(), from.getRow());
        int ny = Integer.compare(to.getCol(), from.getCol());

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

    private boolean isCannonValidTarget(final Position from, final Position to, final Board board, final int pieceCount) {
        if (!isCannon(board, to.getRow(), to.getCol())
                && board.isAnotherTeam(from, to)
                && pieceCount == CANNON_REQUIRED_PIECE_COUNT) {
            return true;
        }
        return false;
    }

    private boolean isCannon(Board board, int row, int col) {
        if (board.findPieceByPosition(Position.of(row, col)).isEmpty()) return false;
        return board.findPieceByPosition(Position.of(row, col)).get().getType() == Type.CANNON;
    }

    private boolean isNotCorrectPath(Position from, Position to) {
        return from.getRow() != to.getRow() && from.getCol() != to.getCol();
    }
}
