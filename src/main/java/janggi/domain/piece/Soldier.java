package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.MoveStrategy;

public class Soldier extends Piece{
    public Soldier(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
    }
}
