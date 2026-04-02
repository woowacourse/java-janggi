package janggi.domain.movestrategy;

import janggi.domain.BoardState;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

public class ChaMoveStrategy implements MoveStrategy {

    @Override
    public boolean canMove(Position from, Position to, BoardState boardState) {
        int fromRow = from.getRow();
        int fromCol = from.getColumn();
        int toRow = to.getRow();
        int toCol = to.getColumn();

        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        if (fromRow == toRow) {
            int start = Math.min(fromCol, toCol) + 1;
            int end = Math.max(fromCol, toCol);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(fromRow), Column.of(i));
                if (boardState.hasPieceAt(position)) {
                    return false;
                }
            }
        }

        if (fromCol == toCol) {
            int start = Math.min(fromRow, toRow) + 1;
            int end = Math.max(fromRow, toRow);

            for (int i = start; i < end; i++) {
                Position position = Position.of(Row.of(i), Column.of(fromCol));
                if (boardState.hasPieceAt(position)) {
                    return false;
                }
            }
        }
        return true;
    }
}
