package domain.piece;

import java.util.Objects;

public abstract class Piece {

    protected final Team team;
    protected final PieceType pieceType;
    // THINK
    // protected final MoveRule moveRule;

    protected Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    protected Piece(PieceType pieceType) {
        this.team = null;
        this.pieceType = pieceType;
    }

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isCho() {
        return this.team == Team.CHO;
    }

    public boolean hasPiece() {
        return this.pieceType != PieceType.NONE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieceType);
    }

}
