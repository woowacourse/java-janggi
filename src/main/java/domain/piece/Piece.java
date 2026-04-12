package domain.piece;

import java.util.Objects;

public record Piece(
        Team team,
        PieceType pieceType
) {

    public static Piece none() {
        return new Piece(Team.NONE, PieceType.NONE);
    }

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isCho() {
        return this.team == Team.CHO;
    }

    public boolean isHan() {
        return this.team == Team.HAN;
    }

    public boolean hasPiece() {
        return this.pieceType != PieceType.NONE;
    }

    public boolean isGeneral() {
        return pieceType.isGeneral();
    }

    public boolean isCannon() {
        return pieceType.isCannon();
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
