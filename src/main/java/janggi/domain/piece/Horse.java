package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.MoveStrategy;

public class Horse extends Piece{
    public Horse(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
    }
}
