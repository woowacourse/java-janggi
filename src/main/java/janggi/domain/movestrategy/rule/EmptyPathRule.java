package janggi.domain.movestrategy.rule;

import janggi.domain.board.BoardState;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

public class EmptyPathRule implements MoveRule {

    @Override
    public boolean isValid(Position from, Position to, BoardState boardState) {
        if (from.getRow() == to.getRow()) {
            int start = Math.min(from.getColumn(), to.getColumn()) + 1;
            int end = Math.max(from.getColumn(), to.getColumn());

            for (int col = start; col < end; col++) {
                if (boardState.hasPieceAt(Position.of(Row.of(from.getRow()), Column.of(col)))) {
                    return false;
                }
            }
        }

        if (from.getColumn() == to.getColumn()) {
            int start = Math.min(from.getRow(), to.getRow()) + 1;
            int end = Math.max(from.getRow(), to.getRow());

            for (int row = start; row < end; row++) {
                if (boardState.hasPieceAt(Position.of(Row.of(row), Column.of(from.getColumn())))) {
                    return false;
                }
            }
        }

        return true;
    }
}
