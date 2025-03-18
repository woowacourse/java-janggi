public class Janggi {
    private final Board board;

    public Janggi(final Board board) {
        this.board = board;
    }

    public void initializeBoard() {
        initializeGeneral();
    }

    private void initializeGeneral() {
        board.putPiece(new Position(9, 5), new Piece(PieceType.GENERAL, TeamType.RED));
        board.putPiece(new Position(2, 5), new Piece(PieceType.GENERAL, TeamType.BLUE));
    }
}
