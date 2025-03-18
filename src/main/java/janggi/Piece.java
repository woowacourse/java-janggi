package janggi;


public class Piece {
    private final PieceType pieceType;
    private final Position position;

    public Piece(PieceType pieceType, Position position) {
        this.pieceType = pieceType;
        this.position = position;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Position getPosition() {
        return position;
    }
}
