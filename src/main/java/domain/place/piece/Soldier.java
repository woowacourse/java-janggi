package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;

public class Soldier extends Piece {

    public Soldier(Side side, MoveStrategy moveStrategy) {
        super(side, moveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.SOLDIER;
    }

    @Override
    public double getScore() {
        return 2;
    }
}
