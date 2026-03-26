package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.MoveStrategy;

public class Advisor extends Piece{
    public Advisor(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
    }
}
