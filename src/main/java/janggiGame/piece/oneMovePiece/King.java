package janggiGame.piece.oneMovePiece;

import janggiGame.piece.Dynasty;

public class King extends OneMovePiece {
    public static final String NAME = "장";

    public King(Dynasty dynasty) {
        super(dynasty);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
