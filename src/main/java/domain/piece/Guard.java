package domain.piece;

import domain.pattern.Direction;
import domain.pattern.Path;
import domain.piece.state.MovedGuard;

public class Guard extends Piece {
    public Guard(Side side) {
        super(3, side, new Path(Direction.createGeneralOrGuardPatternMap()), new MovedGuard(side));
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GUARD;
    }
}
