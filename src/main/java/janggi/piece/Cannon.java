package janggi.piece;

import janggi.board.Board;
import janggi.board.Position;
import java.util.List;

public class Cannon extends Piece {

    private static final int MUST_JUMP_PIECE_COUNT = 1;

    public Cannon(final Country country) {
        super(country);
    }

    @Override
    protected boolean canMove(final Position now, final Position destination, final Board board) {
        if (!now.isSameLine(destination) || board.isCannonByPosition(destination)) {
            return false;
        }

        final List<Position> positions = now.calculateBetweenPositions(destination);
        final int pieceCountInPositions = board.calculatePieceCountByPositions(positions);

        if (pieceCountInPositions != MUST_JUMP_PIECE_COUNT || board.containsCannonByPositions(positions)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
