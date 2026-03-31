package domain.place.piece;

import domain.place.moveStrategy.MoveStrategy;
import domain.place.palaceMoveStrategy.PalaceMoveStrategy;

public class Soldier extends Piece {

    public Soldier(Side side, MoveStrategy moveStrategy, PalaceMoveStrategy palaceMoveStrategy) {
        super(side, moveStrategy, palaceMoveStrategy);
    }

    @Override
    public PieceSymbol getSymbol() {
        return PieceSymbol.SOLDIER;
    }

}
