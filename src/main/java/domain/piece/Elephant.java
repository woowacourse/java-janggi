package domain.piece;

import domain.game.Side;

public class Elephant extends Piece {

    public Elephant(Side side) {
        super(side);
    }

    @Override
    public String toString() {
        return "Elephant{" +
                "side=" + side +
                '}';
    }
}
