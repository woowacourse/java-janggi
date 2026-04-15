package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;

public class Guard extends Piece {

    public Guard(Side side, MoveStrategy moveStrategy) {
        super(side, moveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.GUARD;
    }

    @Override
    public double getScore() {
        return 3;
    }
}
