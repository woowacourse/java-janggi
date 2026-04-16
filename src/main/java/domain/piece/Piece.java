package domain.piece;

import domain.team.Team;

public class Piece {
    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public Piece(PieceType pieceType) {
        this.team = Team.NONE;
        this.pieceType = pieceType;
    }

    public PieceType pieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isSameTeam(Team team) {
        return this.team.isSameTeam(team);
    }

    public boolean isSamePiece(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public boolean hasPiece() {
        return this.pieceType != PieceType.NONE;
    }
}
