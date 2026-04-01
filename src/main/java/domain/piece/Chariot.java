package domain.piece;

import domain.coordination.Coordination;
import domain.piece.error.PieceException;

import java.util.List;

public class Chariot extends Piece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CHARIOT;
    }

    @Override
    public void validateRule(Coordination from, Coordination to) {
        boolean movable = from.isHorizontal(to) || from.isVertical(to);
        if (!movable) {
            throw new PieceException(IMPOSSIBLE_MOVE_MESSAGE);
        }
    }

    @Override
    public List<Coordination> resolvePath(Coordination from, Coordination to) {
        if (from.isVertical(to)) {
            return from.verticalPathTo(to);
        }
        return from.horizontalPathTo(to);
    }
}
