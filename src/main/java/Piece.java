public abstract class Piece {
    final PieceColor color;

    Piece(PieceColor color) {
        this.color = color;
    }

    public abstract boolean canMove(int row, int col, int newRow, int newCol);
}
