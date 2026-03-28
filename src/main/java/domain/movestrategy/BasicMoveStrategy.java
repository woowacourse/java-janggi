package domain.movestrategy;

import domain.board.Board;
import domain.piece.Piece;
import domain.piece.Position;
import java.util.Map;

public abstract class BasicMoveStrategy implements MoveStrategy {

    protected boolean isInsideBoard(final Position position) {
        return position.column() >= Board.MIN_COLUMN_RANGE && position.column() <= Board.MAX_COLUMN_RANGE
                && position.row() >= Board.MIN_ROW_RANGE && position.row() <= Board.MAX_ROW_RANGE;
    }

    protected boolean isEmptyOrOpposite(final Position from, final Position to, final Map<Position, Piece> pieces) {
        return !pieces.containsKey(to) || !pieces.get(from).isSameTeam(pieces.get(to));
    }
}
