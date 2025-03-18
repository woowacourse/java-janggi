package chessPiece;

public abstract class ChessPiece {

    private final String name;
    private final BoardPosition boardPosition;

    ChessPiece(final String name, final BoardPosition boardPosition) {
        this.name = name;
        this.boardPosition = boardPosition;
    }

    public BoardPosition getBoardPosition() {
        return boardPosition;
    }

    public String getName() {
        return name;
    }
}
