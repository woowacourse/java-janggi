package janggi.model.piece;

import janggi.model.Team;
import janggi.model.position.PositionPath;
import janggi.model.position.Position;
import java.util.List;

public abstract class Piece {
    protected final Team team;

    protected Piece(Team team) {
        this.team = team;
    }

    public abstract PositionPath getLegalPath(Position from, Position to);

    public abstract boolean canPassThrough(List<Piece> piecesOnPath, Piece pieceAtTo);

    public abstract boolean canPassThrough(List<Piece> piecesOnPath);

    public boolean isSameTeam(Piece other) {
        return this.team.equals(other.team);
    }

    public boolean isSameTeam(Team other) {
        return this.team.equals(other);
    }
}
