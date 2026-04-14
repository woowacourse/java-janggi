package domain.piece;

import domain.coordinate.Position;
import domain.Side;
import domain.rule.CannonRule;
import domain.strategy.LinearStrategy;

import java.util.List;

public final class Cannon extends Piece {

    public Cannon(Side side) {
        super(side, new LinearStrategy(), new CannonRule());
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public Piece withSide(Side side) {
        return new Cannon(side);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
