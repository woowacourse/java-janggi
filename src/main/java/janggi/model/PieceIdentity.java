package janggi.model;

import java.util.Objects;

public class PieceIdentity {
    private final Color color;
    private final PieceType pieceType;

    public PieceIdentity(Color color, PieceType pieceType) {
        this.color = color;
        this.pieceType = pieceType;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PieceIdentity identity)) {
            return false;
        }
        return getColor() == identity.getColor() && getPieceType() == identity.getPieceType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColor(), getPieceType());
    }
}
