package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Guard extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.GUARD;

    public Guard(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }

    @Override
    public Guard move(final Position target, final MoveInfos moveInfos) {
        return new Guard(target, directions);
    }
}
