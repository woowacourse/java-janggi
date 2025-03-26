package domain.piece.state;

import domain.piece.Side;

public class MovedGuard extends MovedInPalace {
    public MovedGuard(Side side) {
        super(side, new Palace());
    }

    @Override
    public PieceState updateState() {
        return new MovedGuard(side);
    }
}
