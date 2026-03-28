package domain.piece;

import java.util.Objects;

public record Piece(
        Team team,
        PieceType pieceType
) {

    public static Piece none() {
        return new Piece(null, PieceType.NONE);
    }

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isCho() {
        return this.team == Team.CHO;
    }

    public boolean isSamePiece(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean hasPiece() {
        return this.pieceType != PieceType.NONE;
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
