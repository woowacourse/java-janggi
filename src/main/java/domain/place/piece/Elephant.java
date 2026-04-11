package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;

public class Elephant extends Piece {

    public Elephant(Side side, MoveStrategy moveStrategy) {
        super(side, moveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.ELEPHANT;
    }

    @Override
    public double getScore() {
        return 3;
    }
}
