package domain.strategy;

import domain.Board;
import domain.Piece;
import domain.Type;
import domain.vo.Position;

import java.util.Optional;

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

        while (row != to.getRow() || col != to.getCol()) {
            row += nx;
            col += ny;

            if (row == to.getRow() && col == to.getCol()) {
                break;
            }

            if (board.isExistPosition(Position.of(row, col))) {
                if (isCannon(board, row, col)) {
                    return false;
                }
                pieceCount += 1;
            }
        }

        return isCannonValidTarget(from, to, board, pieceCount);
    }

    private boolean isCannonValidTarget(final Position from, final Position to, final Board board, final int pieceCount) {
        return !isCannon(board, to.getRow(), to.getCol())
                && board.canOccupy(from, to)
                && pieceCount == CANNON_REQUIRED_PIECE_COUNT;
    }

    private boolean isCannon(Board board, int row, int col) {
        Optional<Piece> piece = board.findPieceByPosition(Position.of(row, col));
        return piece.isPresent() && piece.get().getType() == Type.CANNON;
    }

    private boolean isNotCorrectPath(Position from, Position to) {
        return from.getRow() != to.getRow() && from.getCol() != to.getCol();
    }
}
