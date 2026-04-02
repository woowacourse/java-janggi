package janggi.domain.movestrategy;

import janggi.domain.BoardState;
import janggi.domain.position.Column;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

public class PoMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        int fromRow = from.getRow();
        int fromCol = from.getColumn();
        int toRow = to.getRow();
        int toCol = to.getColumn();

        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        int jumpCount = 0;

        if (fromRow == toRow) {
            int start = Math.min(fromCol, toCol) + 1;
            int end = Math.max(fromCol, toCol);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(fromRow), Column.of(i));
                if (boardState.hasPieceAt(position)) {
                    Piece jumpPiece = boardState.getPieceAt(position);
                    if (jumpPiece.getPieceType() == PieceType.PO) {
                        return false;
                    }
                    jumpCount++;
                }
            }
        }

        if (fromCol == toCol) {
            int start = Math.min(fromRow, toRow) + 1;
            int end = Math.max(fromRow, toRow);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(i), Column.of(fromCol));
                if (boardState.hasPieceAt(position)) {
                    Piece jumpPiece = boardState.getPieceAt(position);
                    if (jumpPiece.getPieceType() == PieceType.PO) {
                        return false;
                    }
                    jumpCount++;
                }
            }
        }

        if (jumpCount != 1) {
            return false;
        }

        if (boardState.hasPieceAt(to)) {
            Piece targetPiece = boardState.getPieceAt(to);
            if (targetPiece.getPieceType() == PieceType.PO) {
                return false;
            }
        }
        return true;
    }
}
