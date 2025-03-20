package piece;

import board.Board;
import board.Position;

public class Guard extends Piece{

    public Guard(final TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board, final TeamType teamType) {
        return now.calculateDistance(destination) == 1;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Guard;
    }
}
