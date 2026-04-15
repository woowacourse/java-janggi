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

    @Override
    public boolean isCannon() {
        return true;
    }

    @Override
    public double getScore() {
        return 7;
    }
}
