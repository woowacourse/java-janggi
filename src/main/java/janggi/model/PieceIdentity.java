package janggi.model;

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
}
