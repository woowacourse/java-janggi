package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;

public class Guard extends Piece{

    public Guard(final Country country) {
        super(country);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        return now.calculateDistance(destination) == 1;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
