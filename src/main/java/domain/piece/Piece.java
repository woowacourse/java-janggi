package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public abstract class Piece {
    protected final Team team;
    protected final PieceType type;

    public Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public Team getTeam() {
        return this.team;
    }

    public final PieceType getType() {
        return this.type;
    }

    public Integer getScore() {
        return this.type.getScore();
    }

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isCannon() {
        return this.type == PieceType.CANNON;
    }

    public abstract boolean canMove(Position from, Position to, Board board);
}
