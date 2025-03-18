public class Janggi {
    private final Board board;

    public Janggi(final Board board) {
        this.board = board;
    }

    public void initializeBoard() {
        initializeGeneral();
        initializeChariot();
    }

    private void initializeGeneral() {
        board.putPiece(new Position(9, 5), new Piece(PieceType.GENERAL, TeamType.BLUE));
        board.putPiece(new Position(2, 5), new Piece(PieceType.GENERAL, TeamType.RED));
    }

    private void initializeChariot() {
        board.putPiece(new Position(0, 1), new Piece(PieceType.CHARIOT, TeamType.BLUE));
        board.putPiece(new Position(0, 9), new Piece(PieceType.CHARIOT, TeamType.BLUE));
        board.putPiece(new Position(1, 1), new Piece(PieceType.CHARIOT, TeamType.RED));
        board.putPiece(new Position(1, 9), new Piece(PieceType.CHARIOT, TeamType.RED));
    }
}
