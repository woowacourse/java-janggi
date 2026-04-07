package janggi.domain.movestorage;

import janggi.domain.BoardView;
import janggi.domain.Column;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Row;

import java.util.List;

public class PoMoveStorage implements MoveStorage {

    @Override
    public boolean canMove(Position from, Position to, BoardView boardState) {
        int fromRow = from.getRowValue();
        int fromColumn = from.getColumnValue();
        int toRow = to.getRowValue();
        int toColumn = to.getColumnValue();

        if (fromRow != toRow && fromColumn != toColumn) {
            return false;
        }

        int jumpCount = 0;

        if (fromRow == toRow) {
            int start = Math.min(fromColumn, toColumn) + 1;
            int end = Math.max(fromColumn, toColumn);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(fromRow), Column.of(i));
                if (boardState.hasPieceAt(position)) {
                    Piece jumpPiece = boardState.getPieceAt(position);
                    if (jumpPiece.getName().equals("包")) {
                        return false;
                    }
                    jumpCount++;
                }
            }
        }

        if (fromColumn == toColumn) {
            int start = Math.min(fromRow, toRow) + 1;
            int end = Math.max(fromRow, toRow);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(i), Column.of(fromColumn));
                if (boardState.hasPieceAt(position)) {
                    Piece jumpPiece = boardState.getPieceAt(position);
                    if (jumpPiece.getName().equals("包")) {
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
            if (targetPiece.getName().equals("包")) {
                return false;
            }
        }
        return true;
    }
}
