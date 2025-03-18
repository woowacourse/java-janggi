package janggi.piece;

import janggi.board.Position;

import java.util.Map;

public abstract class Piece {

    protected final String team;

    public Piece(String team) {
        this.team = team;
    }

    public boolean isDifferentTeam(String team) {
        return this.team.equals(team);
    }

    public abstract boolean canMove(Map<Position, Piece> board, Position start, Position goal);

}
