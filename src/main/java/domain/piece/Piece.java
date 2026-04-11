package domain.piece;

public class Piece {
    private final Team team;
    private final PieceType pieceType;

    public Piece(Team team, PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean isOwnedBy(Team targetTeam) {
        return this.team == targetTeam;
    }

    public boolean isSameTeamAs(Piece other) {
        return this.team == other.team;
    }

    public Team getTeam() {
        return team;
    }
}
