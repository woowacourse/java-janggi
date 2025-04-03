package janggi.piece;

import janggi.position.Position;
import java.util.List;

public abstract class Piece {

    protected final Color color;

    public Piece(final Color color) {
        this.color = color;
    }

    public boolean isSameColor(final Piece other) {
        return this.color == other.color;
    }

    public boolean isRed() {
        return color == Color.RED;
    }

    public Color getSide() {
        return color;
    }

    public abstract boolean isMoveable(final Position start, final Position end, final Pieces pieces);

    public abstract List<Position> calculatePath(final Position start, final Position end);

    public abstract PieceType getPieceType();

    public abstract int getScore();

    public String getColorMessage() {
        if (color == Color.RED) {
            return "RED";
        }
        return "BLUE";
    }

    public boolean isSamePieceType(final Piece other) {
        return this.getPieceType() == other.getPieceType();
    }
}
