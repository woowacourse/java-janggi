package pieces;

import java.util.List;
import movepolicy.MoveContext;
import movepolicy.destination.DestinationRule;
import movepolicy.path.PathRule;
import position.Position;

public abstract class FullPiece implements Piece {

    private final Side side;

    public FullPiece(Side side) {
        this.side = side;
    }

    public final boolean isHan() {
        return side.isHan();
    }

    public final boolean isCho() {
        return side.isCho();
    }

    public final boolean isSameSide(FullPiece destinationPiece) {
        if (isHan() && destinationPiece.isHan()) {
            return true;
        }
        return isCho() && destinationPiece.isCho();
    }
}
