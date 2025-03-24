package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;

public class Soldier extends Piece {

    public Soldier(final Country country) {
        super(country);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        if (country == Country.HAN) {
            return now.calculateDistance(destination) == 1 && now.isXLessThan(destination);
        }
        return now.calculateDistance(destination) == 1 && now.isXGreaterThan(destination);
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Soldier;
    }
}
