package janggi.piece;

import janggi.board.JanggiScore;
import janggi.board.VisibleBoard;
import janggi.coordinate.Position;
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
    protected boolean canMove(final Position now, final Position destination, final VisibleBoard visibleBoard) {
        if (!now.isSameLine(destination)) {
            if (isCornerToCornerInPalace(now, destination, Country.HAN)) {
                return visibleBoard.existPieceByPosition(Position.PALACE_CENTER_HAN)
                        && !visibleBoard.containsCannonByPositions(List.of(Position.PALACE_CENTER_HAN));
            }
            if (isCornerToCornerInPalace(now, destination, Country.CHO)) {
                return visibleBoard.existPieceByPosition(Position.PALACE_CENTER_CHO)
                        && !visibleBoard.containsCannonByPositions(List.of(Position.PALACE_CENTER_CHO));
            }
        }

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

    private static boolean isCornerToCornerInPalace(final Position now, final Position destination,
                                                    final Country country) {
        if (now.isCornerInPalace(country) && destination.isCornerInPalace(country)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
