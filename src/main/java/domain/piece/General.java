package domain.piece;

import domain.pattern.Direction;
import domain.pattern.Path;
import domain.piece.state.MovedGeneral;

public class General extends Piece {
    private static final int NO_SCORE = 0;

    public General(Side side) {
        super(NO_SCORE, side, new Path(Direction.createGeneralOrGuardPatternMap()), new MovedGeneral(side));
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
