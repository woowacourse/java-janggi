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

    public abstract List<Position> calculatePath(final Position start, final Position end);

    public abstract PieceType getPieceType();

    public boolean isCannon() {
        return false;
    }
}
