package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.MoveStrategy;

public class General extends Piece{
    public General(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
    }
}
