package domain.place.piece;

import domain.place.Place;

public abstract class Piece implements Place {
    protected final Side side;

    // Todo 무브 인터페이스 추가

    public Piece(Side side) {
        this.side = side;
    }

    @Override
    public String getFormat(){
        return side.getName() + getSymbol().display();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public abstract PieceSymbol getSymbol();
}

