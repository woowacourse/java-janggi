package domain.piece;

import static domain.player.Team.CHO;
import static domain.player.Team.HAN;

import domain.player.Team;

public class Piece {

    private final Team team;
    private final PieceType pieceType;

    // FIXME : 진영별 공통 처리
    public static Piece of(final PieceType pieceType, final Team team) {
        return new Piece(team, pieceType);
    }

    public static Piece choPieceOf(final PieceType pieceType) {
        return new Piece(CHO, pieceType);
    }

    public static Piece hanPieceOf(final PieceType pieceType) {
        return new Piece(HAN, pieceType);
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
