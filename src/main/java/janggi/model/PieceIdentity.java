package janggi.model;

public record PieceIdentity(Color color, PieceType pieceType) {

    public PieceType getPieceType() {
        return pieceType;
    }

    public double score() {
        if (color == Color.RED && pieceType == PieceType.KING) {
            return pieceType.score() + 1.5;
        }
        return pieceType().score();
    }
}
