package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.Position;

public class General extends Piece {

    private static final String NAME = "K";

    public General(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public General(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public General updatePosition(final Position position) {
        return new General(position, directions);
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    public String getName() {
        return NAME;
    }
}
