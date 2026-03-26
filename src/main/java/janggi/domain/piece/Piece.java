package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.MoveStrategy;

public abstract class Piece {
    private final Camp camp;
    private final MoveStrategy moveStrategy;

    Piece(Camp camp, MoveStrategy moveStrategy) {
        this.camp = camp;
        this.moveStrategy = moveStrategy;
    }
}
