package domain.board.wing;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.piece.Piece;
import java.util.Map;

public abstract class Wing {

    protected static final MoveAmount FAR_FROM_BASE_ROW = new MoveAmount(0);

    protected final Piece first;
    protected final Piece second;

    protected Wing(WingPieces wingPieces) {
        this.first = wingPieces.first();
        this.second = wingPieces.second();
    }

    public abstract Map<Intersection, Piece> setUpPieces(Side side);
}
