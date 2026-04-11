package domain.piece;

import domain.Board;
import domain.PieceType;
import domain.Position;
import domain.Team;

public abstract class Piece {
    private final Team team;
    private final PieceType type;

    public Piece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    public PieceType getType() {
        return this.type;
    }

    public Team getTeam() {return this.team;}

    public boolean isSameTeam(Piece other) {
        return this.team == other.team;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public boolean isSameType(PieceType type) {
        return this.type == type;
    }

    public boolean isSameType(Piece piece) {
        return this.type == piece.getType();
    }

    public int getPieceScore() {
        return type.getScore();
    }

    public abstract boolean canMove(Position from, Position to, Board board);
}
