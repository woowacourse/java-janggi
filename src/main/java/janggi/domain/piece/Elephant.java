package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.MoveStrategy;

public class Elephant extends Piece{
    public Elephant(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
    }
}
