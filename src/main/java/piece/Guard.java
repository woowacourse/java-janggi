package piece;

import board.Board;
import position.Position;
import position.PositionFactory;
import validator.PalaceCheckable;

public class Guard extends Piece implements PalaceCheckable {

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
        return 3;
    }
}
