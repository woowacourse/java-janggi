package domain.piece;

import domain.board.Board;
import domain.position.Position;
import domain.position.PositionFactory;
import validator.PalaceCheckable;

public class Guard extends Piece implements PalaceCheckable {

    private static final int GUARD_SCORE = 3;

    public Guard(final Position position, final Country country) {
        super(position, country);
    }

    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateBound(dest, country);
        PositionFactory.validateAdjacentPositionBy(src, dest);
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Guard;
    }

    @Override
    public int getScore() {
        return GUARD_SCORE;
    }
}
