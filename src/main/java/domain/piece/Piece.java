package domain.piece;

import domain.player.Team;

public class Piece {

    private final Team team;
    private final PieceType pieceType;

    public static Piece of(final PieceType pieceType, final Team team) {
        return new Piece(team, pieceType);
    }

    private Piece(final Team team, final PieceType pieceType) {
        this.team = team;
        this.pieceType = pieceType;
    }


    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isCannon() {
        return pieceType == PieceType.CANNON;
    }


    public Team opponentTeam() {
        return this.team.opponent();
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }
}
