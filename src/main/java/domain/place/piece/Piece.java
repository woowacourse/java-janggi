package domain.place.piece;

import domain.place.Place;
import domain.place.moveStrategy.MoveStrategy;

public abstract class Piece implements Place {
    protected final Side side;
    protected final MoveStrategy moveStrategy;

    public Piece(Side side, MoveStrategy moveStrategy) {
        this.side = side;
        this.moveStrategy = moveStrategy;
    }

    public abstract PieceSymbol getSymbol();

    @Override
    public String getFormat() {
        return side.getName() + getSymbol().display();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isSameSide(Side side) {
        return this.side == side;
    }

    @Override
    public Side getSide() {
        return side;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}

