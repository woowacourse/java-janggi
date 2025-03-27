package domain.piece;

import domain.pattern.GeneralPath;
import domain.piece.state.MovedGeneral;

public class General extends Piece {
    private static final int NO_SCORE = 0;

    public General(Side side) {
        super(NO_SCORE, side, new GeneralPath(), new MovedGeneral(side));
    }

    @Override
    public PieceSymbol getPieceSymbol() {
        return PieceSymbol.GENERAL;
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
