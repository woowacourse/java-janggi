package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
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

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    abstract boolean canMove(Position from, Position to, Board board);
}
