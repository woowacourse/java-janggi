package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(final Country country) {
        super(country);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        if (!now.isSameLine(destination) || board.equalsTypeByPositionAndPiece(destination, this)) {
            return false;
        }

        int count = 0;
        final List<Position> positions = now.calculateBetweenPositions(destination);
        for (final Position position : positions) {
            if (board.existPieceByPosition(position)) {
                count++;

                if (board.equalsTypeByPositionAndPiece(position, this)) {
                    return false;
                }
            }
        }

        if (count != 1) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equalsType(final Piece piece) {
        return piece instanceof Cannon;
    }
}
