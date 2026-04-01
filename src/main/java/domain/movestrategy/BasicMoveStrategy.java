package domain.movestrategy;

import domain.piece.Piece;
import domain.board.Position;
import java.util.Map;

public abstract class BasicMoveStrategy implements MoveStrategy {

    protected boolean isInsideBoard(final Position position) {
        return position.isInside();
    }

    protected boolean isEmptyOrOpposite(final Position from, final Position to, final Map<Position, Piece> pieces) {
        return !pieces.containsKey(to) || !pieces.get(from).isSameTeam(pieces.get(to));
    }
}
