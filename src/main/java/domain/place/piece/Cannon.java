package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;

public class Cannon extends Piece {

    public Cannon(Side side, MoveStrategy moveStrategy) {
        super(side, moveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.CANNON;
    }
}
