package domain.piece;

import domain.board.Board;
import domain.position.Position;
import domain.position.PositionFactory;
import validator.PalaceCheckable;

public class General extends Piece implements PalaceCheckable {

    private static final int GENERAL_SCORE = 3;

    public General(final Position position, final Country country) {
        super(position, country);
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateBound(dest, country);
        PositionFactory.validateAdjacentPositionBy(src, dest);
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof General;
    }

    @Override
    public int getScore() {
        return GENERAL_SCORE;
    }
}
