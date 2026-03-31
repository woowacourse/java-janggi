package janggi.domain.mouveRule;

import janggi.domain.BoardView;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.vo.Position;

public class CannonMoveRule implements MoveRule {

    @Override
    public boolean canMove(Position from, Position to, BoardView board) {
        int fromRow = from.getRow();
        int fromCol = from.getCol();
        int toRow = to.getRow();
        int toCol = to.getCol();

        if (!isStraightLine(fromRow, fromCol, toRow, toCol)) {
            return false;
        }

        int jumpedPieceCount = countPiecesBetween(board, fromRow, fromCol, toRow, toCol);
        if (jumpedPieceCount != 1) {
            return false;
        }

        Piece bridgePiece = findBridgePiece(board, fromRow, fromCol, toRow, toCol);
        Piece targetPiece = board.findByPosition(to);
        if (isCannon(bridgePiece) || isCannon(targetPiece)) {
            return false;
        }
        return true;
    }

    private boolean isStraightLine(int fromRow, int fromCol, int toRow, int toCol) {
        return fromRow == toRow || fromCol == toCol;
    }

    private int countPiecesBetween(BoardView board, int fromRow, int fromCol, int toRow, int toCol) {
        int count = 0;

        if (fromRow == toRow) {
            int start = Math.min(fromCol, toCol);
            int end = Math.max(fromCol, toCol);

            for (int col = start + 1; col < end; col++) {
                if (!board.isEmptyPosition(new Position(fromRow, col))) {
                    count++;
                }
            }
            return count;
        }

        int start = Math.min(fromRow, toRow);
        int end = Math.max(fromRow, toRow);

        for (int row = start + 1; row < end; row++) {
            if (!board.isEmptyPosition(new Position(row, fromCol))) {
                count++;
            }
        }
        return count;
    }

    private Piece findBridgePiece(BoardView board, int fromRow, int fromCol, int toRow, int toCol) {
        if (fromRow == toRow) {
            int start = Math.min(fromCol, toCol);
            int end = Math.max(fromCol, toCol);

            for (int col = start + 1; col < end; col++) {
                Position position = new Position(fromRow, col);
                if (!board.isEmptyPosition(position)) {
                    return board.findByPosition(position);
                }
            }
            return null;
        }

        int start = Math.min(fromRow, toRow);
        int end = Math.max(fromRow, toRow);

        for (int row = start + 1; row < end; row++) {
            Position position = new Position(row, fromCol);
            if (!board.isEmptyPosition(position)) {
                return board.findByPosition(position);
            }
        }
        return null;
    }

    private boolean isCannon(Piece piece) {
        return piece instanceof Cannon;
    }

}
