package janggiGame.piece.oneMovePiece;

import janggiGame.piece.Dynasty;

public class Advisor extends OneMovePiece {
    public static final String NAME = "사";

    public Advisor(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
