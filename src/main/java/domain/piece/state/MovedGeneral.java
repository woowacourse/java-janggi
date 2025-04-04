package domain.piece.state;

import domain.piece.Side;

public class MovedGeneral extends MovedInPalace {
//    private MovedInPalace original;

    public MovedGeneral(Side side) {
        super(side, new Palace());
    }

    @Override
    public PieceState updateState() {
        return new MovedGeneral(side);
    }
}
