package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;
import java.util.List;

public class Elephant extends Piece{

    private static final double ELEPHANT_DISTANCE = Math.sqrt(13);

    public Elephant(final Country country) {
        super(country);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        if (now.calculateDistance(destination) != ELEPHANT_DISTANCE) {
            return false;
        }

        final List<Position> positions = now.calculateElephantMiddlePositions(destination);
        for (final Position position : positions) {
            if (board.existPieceByPosition(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
