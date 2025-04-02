package janggi.model;

public record PieceIdentity(Color color, PieceType pieceType) {

    public PieceType getPieceType() {
        return pieceType;
    }
}
