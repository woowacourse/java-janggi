package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;

public class Elephant extends Piece {

    public Elephant(Side side, MoveStrategy moveStrategy, PalaceMoveStrategy palaceMoveStrategy) {
        super(side, moveStrategy, palaceMoveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.ELEPHANT;
    }

}
