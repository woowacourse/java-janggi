package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.Position;
import java.util.List;

public class Chariot extends Piece{

    private static final int CAN_JUMP_PIECE_COUNT = 0;
    private static final JanggiScore KILL_JANGGI_SCORE = new JanggiScore(13);

    public Chariot(final Country country) {
        super(country);
    }

    @Override
    public JanggiScore plusScore(final JanggiScore janggiScore) {
        return KILL_JANGGI_SCORE.plus(janggiScore);
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

}
