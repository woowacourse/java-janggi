package chessPiece;

import java.util.Objects;

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

    public abstract ChessPiece move(BoardPosition boardPosition);

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
        return name;
    }
}
