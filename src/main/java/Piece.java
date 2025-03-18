public abstract class Piece {
    final PieceColor color;

    Piece(PieceColor color) {
        this.color = color;
    }

    public abstract boolean canMove(Position source, Position destination);
}
