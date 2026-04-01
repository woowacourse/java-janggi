package domain.piece;

import domain.PieceProvider;
import domain.position.Position;
import domain.Team;

public abstract class Piece {
    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean canMove(Position from, Position to, PieceProvider pieceProvider);
}
