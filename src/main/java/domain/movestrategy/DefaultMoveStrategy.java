package domain.movestrategy;

import domain.board.Board;
import domain.piece.Position;

public abstract class DefaultMoveStrategy implements MoveStrategy {

    public boolean inBoard(final Position current) {
        return (current.column() >= Board.MIN_COLUMN_RANGE && current.column() <= Board.MAX_COLUMN_RANGE)
                && (current.row() >= Board.MIN_ROW_RANGE && current.row() <= Board.MAX_ROW_RANGE);
    }
}
