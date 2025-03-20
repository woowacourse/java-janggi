package chessPiece;

import java.util.Objects;

public abstract class ChessPiece {

    private final PieceProfile pieceProfile;
    protected BoardPosition boardPosition;

    public ChessPiece(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        this.pieceProfile = pieceProfile;
        this.boardPosition = boardPosition;
    }

    public BoardPosition getBoardPosition() {
        return boardPosition;
    }

    public abstract boolean isMove(BoardPosition boardPosition);

    public abstract void updateChessPiecePositionBy(BoardPosition boardPosition);

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChessPiece that = (ChessPiece) o;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getBoardPosition(),
                that.getBoardPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBoardPosition());
    }

    public String getName() {
        return pieceProfile.getName();
    }

    public PieceProfile getPieceProfile() {
        return pieceProfile;
    }
}
