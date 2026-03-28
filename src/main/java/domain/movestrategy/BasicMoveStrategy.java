package domain.movestrategy;

import domain.board.Board;
import domain.piece.Position;

public abstract class BasicMoveStrategy implements MoveStrategy {

    public boolean isInsideBoard(final Position position) {
        return position.column() >= Board.MIN_COLUMN_RANGE && position.column() <= Board.MAX_COLUMN_RANGE
                && position.row() >= Board.MIN_ROW_RANGE && position.row() <= Board.MAX_ROW_RANGE;
    }
}
