package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;

public class Horse extends Piece {

    private static final double HORSE_DISTANCE = Math.sqrt(5);

    public Horse(final Country country) {
        super(country);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        if (now.calculateDistance(destination) != HORSE_DISTANCE) {
            return false;
        }
        final Position position = now.calculateHorseMiddlePosition(destination);
        if (board.existPieceByPosition(position)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
