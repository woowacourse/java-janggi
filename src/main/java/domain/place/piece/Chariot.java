package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;

public class Chariot extends Piece {

    public Chariot(Side side, MoveStrategy moveStrategy) {
        super(side, moveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.CHARIOT;
    }

    @Override
    public double getScore() {
        return 13;
    }
}
