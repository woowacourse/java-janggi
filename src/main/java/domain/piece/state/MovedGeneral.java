package domain.piece.state;

import domain.piece.Side;

public class MovedGeneral extends MovedInPalace {
    public MovedGeneral(Side side) {
        super(side, new Palace());
    }

    @Override
    public PieceState updateState() {
        return new MovedGeneral(side) {
        };
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
