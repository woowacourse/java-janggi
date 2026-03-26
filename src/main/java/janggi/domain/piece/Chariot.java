package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.MoveStrategy;

public class Chariot extends Piece{
    public Chariot(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
    }
}
