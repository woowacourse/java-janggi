package piece;

import board.Board;
import board.Position;
import java.util.List;

public class Chariot extends Piece{

    public Chariot(final TeamType teamType) {
        super(teamType);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        if (!now.isSameLine(destination)) {
            return false;
        }
        final List<Position> positions = now.calculateBetweenPositions(destination);
        for (final Position position : positions) {
            if (board.existPieceByPosition(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Chariot;
    }
}
