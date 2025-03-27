package janggi.view.format;

import janggi.piece.Piece;

public abstract class PieceFormat {
    private static final String RED_COLOR_FORMAT = "\u001B[31m%s\u001B[0m";
    private static final String BLUE_COLOR_FORMAT = "\u001B[36m%s\u001B[0m";

    public String getMessage(final Piece piece) {
        if (piece.isRed()) {
            return String.format(RED_COLOR_FORMAT, getPieceMessage(piece));
        }
        return String.format(BLUE_COLOR_FORMAT, getPieceMessage(piece));
    }

    protected abstract String getPieceMessage(final Piece piece);
}
