package domain.activePiece;

import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;

public abstract class ActivePiece implements Piece {
    private final Team team;
    private final PieceType type;

    protected ActivePiece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public boolean isSameTeam(Team other) {
        return this.team == other;
    }
}
