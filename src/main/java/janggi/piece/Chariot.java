package janggi.piece;

import janggi.board.VisibleBoard;
import janggi.coordinate.Position;
import java.util.List;

public class Chariot extends Piece{

    private static final int CAN_JUMP_PIECE_COUNT = 0;

    public Chariot(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        if (!now.isSameLine(destination)) {
            return false;
        }
        final List<Position> positions = now.calculateBetweenPositions(destination);
        final int pieceCountInPath = visibleBoard.calculatePieceCountByPositions(positions);

        return pieceCountInPath == CAN_JUMP_PIECE_COUNT;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
