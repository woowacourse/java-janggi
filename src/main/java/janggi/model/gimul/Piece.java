package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.board.PositionPath;
import janggi.model.board.position.Position;
import java.util.List;

public abstract class Piece {
    protected final Team team;

    protected Piece(Team team) {
        this.team = team;
    }

    public abstract PositionPath getLegalPath(Position from, Position to);

    public abstract boolean canPassThrough(List<Piece> gimulsOnPath, Piece pieceAtTo);

    public abstract boolean canPassThrough(List<Piece> gimulsOnPath);

    public boolean isSameTeam(Piece other) {
        return this.team.equals(other.team);
    }

    public boolean isSameTeam(Team other) {
        return this.team.equals(other);
    }
}
