package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;

public class General extends Piece {

    public General(Side side, MoveStrategy moveStrategy) {
        super(side, moveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.GENERAL;
    }
}
