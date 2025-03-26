package domain.piece.category;

import domain.MoveInfos;
import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Soldier extends Piece {

    private static final PieceCategory CATEGORY = PieceCategory.SOLDIER;

    public Soldier(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Soldier move(final Position position, final MoveInfos moveInfos) {
        return new Soldier(position, directions);
    }

    @Override
    public PieceCategory getCategory() {
        return CATEGORY;
    }
}
