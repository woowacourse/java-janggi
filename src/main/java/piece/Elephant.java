package piece;

import board.Board;
import board.Position;
import java.util.List;

public class Elephant extends Piece{

    public Elephant(final TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board, final TeamType teamType) {
        if (now.calculateDistance(destination) != Math.sqrt(13)) {
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
    public boolean equalsType(final Piece piece) {
        return piece instanceof Elephant;
    }
}
