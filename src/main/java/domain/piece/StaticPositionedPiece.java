package domain.piece;

import domain.board.Intersection;
import domain.game.Side;
import java.util.List;

public abstract class StaticPositionedPiece extends Piece {

    public StaticPositionedPiece(Side side) {
        super(side);
    }

    public abstract List<Intersection> initAt();
}
