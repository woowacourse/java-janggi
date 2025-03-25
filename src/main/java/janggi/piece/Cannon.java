package janggi.piece;

import janggi.board.VisibleBoard;
import janggi.coordinate.Position;
import java.util.List;

public class Cannon extends Piece {

    private static final int MUST_JUMP_PIECE_COUNT = 1;

    public Cannon(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        if (!now.isSameLine(destination) || visibleBoard.isCannonByPosition(destination)) {
            return false;
        }

        final List<Position> positions = now.calculateBetweenPositions(destination);
        final int pieceCountInPositions = visibleBoard.calculatePieceCountByPositions(positions);

        if (pieceCountInPositions != MUST_JUMP_PIECE_COUNT || visibleBoard.containsCannonByPositions(positions)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
