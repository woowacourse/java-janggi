package domain.strategy;

import domain.board.Board;
import domain.board.Piece;
import domain.board.Type;
import domain.vo.Position;

import java.util.Optional;

public class CannonMoveStrategy implements MoveStrategy {

    private static final int CANNON_REQUIRED_PIECE_COUNT = 1;

    @Override
    public boolean canMove(final Position from, final Position to, final Board board) {
        if (isNotStraightPath(from, to) && isNotDiagonalPath(from, to, board))
            return false;

        int dx = Integer.compare(to.getRow(), from.getRow());
        int dy = Integer.compare(to.getCol(), from.getCol());

        int pieceCount = 0;
        int row = from.getRow();
        int col = from.getCol();

        while (row != to.getRow() || col != to.getCol()) {
            row += dx;
            col += dy;

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

        return isValidCannonTarget(from, to, board, pieceCount);
    }

    private boolean isNotStraightPath(Position from, Position to) {
        return from.getRow() != to.getRow() && from.getCol() != to.getCol();
    }

    private boolean isNotDiagonalPath(Position from, Position to, Board board) {
        if (board.canMoveDiagonallyInPalace(from, to)
                && Math.abs(from.getRow() - to.getRow()) == Math.abs(from.getCol() - to.getCol())) {
            return false;
        }
        return true;
    }

    private boolean isValidCannonTarget(final Position from, final Position to, final Board board, final int pieceCount) {
        return !isCannon(board, to.getRow(), to.getCol())
                && board.canOccupy(from, to)
                && pieceCount == CANNON_REQUIRED_PIECE_COUNT;
    }

    private boolean isCannon(Board board, int row, int col) {
        Optional<Piece> piece = board.findPieceByPosition(Position.of(row, col));
        return piece.isPresent() && piece.get().getType() == Type.CANNON;
    }
}
