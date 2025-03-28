package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.JanggiPosition;
import java.util.List;

public class Chariot extends Piece {

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
    protected boolean canMove(final JanggiPosition now, final JanggiPosition destination, final VisibleBoard visibleBoard) {
        if (!now.isSameLine(destination)) {
            return canMoveInPalace(now, destination, visibleBoard);
        }

        final List<JanggiPosition> janggiPositions = now.calculateBetweenPositions(destination);
        final int pieceCountInPath = visibleBoard.calculatePieceCountByPositions(janggiPositions);

        return pieceCountInPath == CAN_JUMP_PIECE_COUNT;
    }

    private static boolean canMoveInPalace(final JanggiPosition now, final JanggiPosition destination,
                                           final VisibleBoard visibleBoard) {
        if (!now.isSamePalace(destination)) {
            return false;
        }

        if (isCornerToCorner(now, destination)) {
            return !visibleBoard.existPieceByPosition(now.calculatePalaceCenterPosition());
        }

        if (isCenterToCorner(now, destination) || isCornerToCenter(now, destination)) {
            return true;
        }

        return false;
    }

    private static boolean isCornerToCorner(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCornerInPalace() && destination.isCornerInPalace();
    }

    private static boolean isCornerToCenter(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCornerInPalace() && destination.isCenterInPalace();
    }

    private static boolean isCenterToCorner(final JanggiPosition now, final JanggiPosition destination) {
        return now.isCenterInPalace() && destination.isCornerInPalace();
    }

}
