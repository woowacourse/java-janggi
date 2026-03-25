package domain.piece;

import java.util.Objects;

public abstract class Piece {

    protected final Team team;
    protected final PieceType pieceType;
    // THINK
    // protected final MoveRule moveRule;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieceType);
    }

}
