package Janggi.piece;

import Janggi.board.Board;
import Janggi.board.Position;
import java.util.List;

public class Chariot extends Piece{

    private static final int CAN_JUMP_PIECE_COUNT = 0;

    public Chariot(final Country country) {
        super(country);
    }

    @Override
    public boolean canMove(final Position now, final Position destination, final Board board) {
        if (!now.isSameLine(destination)) {
            return false;
        }
        final List<Position> positions = now.calculateBetweenPositions(destination);
        final int pieceCountInPath = board.calculatePieceCountByPositions(positions);

        return pieceCountInPath == CAN_JUMP_PIECE_COUNT;
    }

    @Override
    public boolean isCannon() {
        return false;
    }
}
