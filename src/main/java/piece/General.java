package piece;

import board.Board;
import position.Position;
import position.PositionFactory;
import validator.PalaceCheckable;

public class General extends Piece implements PalaceCheckable {

    public General(final Position position, final Country country) {
        super(position, country);
    }

    // TODO 2025. 3. 29. 17:35: PositionFactory에 Position들을 넘기는 게 올바른 책임인가?
    @Override
    public void validateMoveCondition(Position src, Position dest, Board board) {
        validateBound(dest);
        PositionFactory.validateAdjacentPositionBy(src, dest);
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof General;
    }
}
