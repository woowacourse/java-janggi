package janggi.piece;

import janggi.position.BoardPosition;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    private final PieceProfile pieceProfile;
    protected BoardPosition boardPosition;

    public Piece(final PieceProfile pieceProfile, final BoardPosition boardPosition) {
        this.pieceProfile = pieceProfile;
        this.boardPosition = boardPosition;
    }

    public BoardPosition getBoardPosition() {
        return boardPosition;
    }

    public abstract boolean isMove(BoardPosition boardPosition);

    public abstract List<BoardPosition> makeRoute(BoardPosition boardPosition);

    public abstract void updateChessPiecePositionBy(BoardPosition boardPosition);

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece that = (Piece) o;
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

    public boolean isChoNation() {
        return pieceProfile.isCho();
    }

    public boolean isHanNation() {
        return pieceProfile.isHan();
    }
}
