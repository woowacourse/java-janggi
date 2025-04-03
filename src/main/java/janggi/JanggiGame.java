package janggi;

import janggi.piece.Color;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.position.Position;
import java.util.Map;

public class JanggiGame {
    private static final double DEOM_SCORE = 1.5;

    private final Pieces pieces;

    public JanggiGame(final Pieces pieces) {
        this.pieces = pieces;
    }

    public JanggiGame() {
        this.pieces = Pieces.init();
    }

    public boolean move(final Position start, final Position end) {
        if (!pieces.isMoveable(start, end)) {
            return false;
        }
        pieces.moveForward(start, end);
        return true;
    }

    public Map<Position, Piece> getBoard() {
        return pieces.getPieces();
    }

    public double getScore(final Color color) {
        if (color == Color.RED) {
            return pieces.calculatePieceScore(color) + DEOM_SCORE;
        }
        return pieces.calculatePieceScore(color);
    }

    public Piece getPiece(final Position position) {
        return pieces.getPieceByPosition(position);
    }
}
