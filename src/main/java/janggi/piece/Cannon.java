package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.JanggiPosition;
import java.util.List;

public class Cannon extends Piece {

    private static final int MUST_JUMP_PIECE_COUNT = 1;
    private static final JanggiScore KILL_JANGGI_SCORE = new JanggiScore(7);

    public Cannon(final Country country) {
        super(country);
    }

    @Override
    public JanggiScore plusScore(final JanggiScore janggiScore) {
        return KILL_JANGGI_SCORE.plus(janggiScore);
    }

    @Override
    protected boolean canMove(final JanggiPosition now, final JanggiPosition destination, final VisibleBoard visibleBoard) {
        if (visibleBoard.isCannonByPosition(destination)) {
            return false;
        }

        if (!now.isSameLine(destination)) {
            return canMoveInPalace(now, destination, visibleBoard);
        }

        final List<JanggiPosition> janggiPositions = now.calculateBetweenPositions(destination);
        final int pieceCountInPositions = visibleBoard.calculatePieceCountByPositions(janggiPositions);

        if (pieceCountInPositions != MUST_JUMP_PIECE_COUNT || visibleBoard.containsCannonByPositions(janggiPositions)) {
            return false;
        }

        return true;
    }

    private boolean canMoveInPalace(final JanggiPosition now, final JanggiPosition destination, final VisibleBoard visibleBoard) {
        if (!now.isSamePalace(destination)) {
            return false;
        }

        if (isCornerToCorner(now, destination)) {
            return hasHurdle(visibleBoard, now);
        }

        return false;
    }

    private boolean hasHurdle(final VisibleBoard visibleBoard, final JanggiPosition now) {
        final List<JanggiPosition> palaceCenter = List.of(now.calculatePalaceCenterPosition());
        return visibleBoard.calculatePieceCountByPositions(palaceCenter) == MUST_JUMP_PIECE_COUNT
                && !visibleBoard.containsCannonByPositions(palaceCenter);
    }

    private boolean isCornerToCorner(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCornerInPalace() && destination.isCornerInPalace();
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
