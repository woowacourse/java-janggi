public class Cell {
    private Piece piece;

    public Cell(final Piece piece) {
        this.piece = new Piece(PieceType.EMPTY, TeamType.NONE);
    }
}
