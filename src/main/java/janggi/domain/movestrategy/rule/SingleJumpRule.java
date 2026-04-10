package janggi.domain.movestrategy.rule;

import janggi.domain.board.BoardState;
import janggi.domain.piece.PieceType;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

public class SingleJumpRule implements MoveRule {

    @Override
    public boolean isValid(Position from, Position to, BoardState boardState) {
        int jumpCount = 0;

        if (from.getRow() == to.getRow()) {
            int start = Math.min(from.getColumn(), to.getColumn()) + 1;
            int end = Math.max(from.getColumn(), to.getColumn());

            for (int col = start; col < end; col++) {
                Position pos = Position.of(Row.of(from.getRow()), Column.of(col));
                if (boardState.hasPieceAt(pos)) {
                    if (boardState.getPieceAt(pos).getPieceType() == PieceType.PO) {
                        return false;
                    }
                    jumpCount++;
                }
            }
        }

        if (from.getColumn() == to.getColumn()) {
            int start = Math.min(from.getRow(), to.getRow()) + 1;
            int end = Math.max(from.getRow(), to.getRow());

            for (int row = start; row < end; row++) {
                Position pos = Position.of(Row.of(row), Column.of(from.getColumn()));
                if (boardState.hasPieceAt(pos)) {
                    if (boardState.getPieceAt(pos).getPieceType() == PieceType.PO) {
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
            if (boardState.getPieceAt(to).getPieceType() == PieceType.PO) {
                return false;
            }
        }

        return true;
    }
}
