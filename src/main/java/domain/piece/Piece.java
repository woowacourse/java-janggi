package domain.piece;

import domain.BoardLocation;
import java.util.List;
import java.util.Objects;

public class Piece {

    private final PieceType pieceType;

    public Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public boolean isMovable(BoardLocation current, BoardLocation target) {
        return pieceType.isMovable(current, target);
    }

    public List<BoardLocation> createAllPath(BoardLocation current, BoardLocation target) {
        return pieceType.createAllPath(current, target);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieceType);
    }
}
