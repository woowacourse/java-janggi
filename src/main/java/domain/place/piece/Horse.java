package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;

public class Horse extends Piece {

    public Horse(Side side, MoveStrategy moveStrategy) {
        super(side, moveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.HORSE;
    }

    @Override
    public double getScore() {
        return 5;
    }
}
