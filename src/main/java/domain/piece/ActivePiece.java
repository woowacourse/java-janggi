package domain.piece;

import domain.Position;

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
