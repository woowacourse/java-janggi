package piece;

import board.Board;
import board.Position;

public class Soldier extends Piece {

    public Soldier(final TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board, final TeamType teamType) {
        if (teamType == TeamType.RED) {
            return now.calculateDistance(destination) == 1 && now.isXLessThan(destination);
        }
        return now.calculateDistance(destination) == 1 && now.isXGreaterThan(destination);
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Soldier;
    }
}
