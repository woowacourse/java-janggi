package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;

public class Soldier extends Piece {

    private static final int SOLDIER_DISTANCE = 1;

    public Soldier(final Country country) {
        super(country);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        if (country == Country.HAN) {
            return now.calculateDistance(destination) == SOLDIER_DISTANCE && now.isXLessThan(destination);
        }
        return now.calculateDistance(destination) == SOLDIER_DISTANCE && now.isXGreaterThan(destination);
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
