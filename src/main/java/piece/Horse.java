package piece;

import board.Board;
import board.Position;

public class Horse extends Piece {

    public Horse(final TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board, final TeamType teamType) {
        if (now.calculateDistance(destination) != Math.sqrt(5)) {
            return false;
        }
        final Position position = now.calculateHorseMiddlePosition(destination);
        if (board.existPieceByPosition(position)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Horse;
    }
}
