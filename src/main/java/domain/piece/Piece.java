package domain.piece;

import domain.PieceType;
import domain.Team;

public abstract class Piece {
    public Team team;
    public PieceType type;

    public Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public PieceType getType() {
        return this.type;
    }
}
