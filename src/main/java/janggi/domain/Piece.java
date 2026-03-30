package janggi.domain;

import java.util.Objects;

public class Piece {

    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public String getTeamName() {
        return team.getName();
    }

    public String getPieceTypeName() {
        return pieceType.getName();
    }


    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isCha() {
        return pieceType == PieceType.CHA;
    }

    public boolean isPo() {
        return pieceType == PieceType.PO;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Piece piece)) {
            return false;
        }
        return team == piece.team && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, pieceType);
    }
}
