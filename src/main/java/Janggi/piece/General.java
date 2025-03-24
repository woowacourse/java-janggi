package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;

public class General extends Piece{

    public General(final Country country) {
        super(country);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        return now.calculateDistance(destination) == 1;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof General;
    }
}
